package com.example.demo;

import com.example.demo.controller.TestController;
import com.example.demo.model.TestXmlDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@AutoConfigureRestDocs
public class ApiDocumentationJUnit5IntegrationTest {

    @InjectMocks
    private TestController testController;

    @Spy
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;


    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.standaloneSetup(testController)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .apply(documentationConfiguration(restDocumentation)).build();
        /*
                .alwaysDo(document(
                        "{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                )).build();
         */
    }

    /*
    @Test
    public void testWithJsonPayload() throws Exception {
        TestDto jsonObject = new TestDto(2L, "Test title", "Test body");
        String jsonPayload = this.objectMapper.writeValueAsString(jsonObject);
        this.mockMvc.perform(post("/test/json")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated())
                .andDo(document("{method-name}",

//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),

                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("is id"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("is title"),
                                fieldWithPath("body").type(JsonFieldType.STRING).description("is body")
                        ),

                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("is id"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("is title"),
                                fieldWithPath("body").type(JsonFieldType.STRING).description("is body")
                        )
                ));
    }
     */

    @Test
    public void testWithXmlPayload() throws Exception {

        /* Stub 만드는 과정은 개선이 필요하다. */
        List<TestXmlDto.Parameter> parameterList = new ArrayList<>();
        parameterList.add(new TestXmlDto.Parameter("JSESSIONID", "stub_JSESSIONID"));
        parameterList.add(new TestXmlDto.Parameter("_xm_webid_1_", "stub_xm_webid"));
        TestXmlDto xmlObject = TestXmlDto.builder()
                .parameters(parameterList)
                .dataset("Testing_dataset")
                .build();

        StringWriter stringWriter = new StringWriter();
        JAXBContext context = JAXBContext.newInstance(TestXmlDto.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.marshal(xmlObject, stringWriter);
        String xmlPayload = stringWriter.toString();

        this.mockMvc.perform(post("/test/xml")
                        .contentType(MediaType.APPLICATION_XML)
                        .content(xmlPayload))
                .andExpect(status().isCreated())
                .andDo(document("{method-name}",

                        /* docs의 표시할 sample을 보기 좋게 출력한다. */
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),

                        /* relaxed[Request/Response]Fields 를 사용한다. */
                        // 까다롭게 필드를 검사할 수 없지만, xml 형식의 특성상 필요 없는 부분이 너무 많으므로 감수한다.
                        relaxedRequestFields(
                                subsectionWithPath("Root/Dataset").type(JsonFieldType.STRING).description("is dataset section")
                        ),
                        relaxedResponseFields(
                                fieldWithPath("Root/Dataset").type(JsonFieldType.STRING).description("실질적인 응답이 담겨있다.")
                        )
                ));
    }
}

    /*
        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andDo(document("index-example",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        links(
                                linkWithRel("crud").description("The CRUD resource")
                        ),
                        responseFields(subsectionWithPath("_links").description("Links to other resources")),
                        responseHeaders(headerWithName("Content-Type").description("The Content-Type of the payload, e.g. `application/hal+json`"))
                ));
    }

        this.mockMvc.perform(post("/crud")
                        .contentType(MediaTypes.HAL_JSON)
                        .content(this.objectMapper.writeValueAsString(crud)))
                .andExpect(status().isCreated())
                .andDo(document("crud-create-example",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("id").description("The id of the input"),
                                fieldWithPath("title").description("The title of the input"),
                                fieldWithPath("body").description("The body of the input"),
                                fieldWithPath("tags").description("An array of tag resource URIs")
                        )
                ));
     */

    /*
    @Test
    public void indexExample() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andDo(document("index-example",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
                        links(linkWithRel("crud").description("The CRUD resource")),
                        responseFields(
                                subsectionWithPath("_links").description("Links to other resources")
                        ),
                        responseHeaders(
                                headerWithName("Content-Type").description("The Content-Type of the payload, e.g. `application/hal+json`")
                        ))
                );
//                        links(linkWithRel("crud").description("The CRUD resource")),
//                        responseFields(subsectionWithPath("_links")
//                                .description("Links to other resources"))
//                        responseHeaders(headerWithName("Content-Type")
//                                .description("The Content-Type of the payload"))));
    }

    @Test
    public void contextLoads() {
    }

     */