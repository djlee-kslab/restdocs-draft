package com.example.demo;

import com.example.demo.controller.TestController;
import com.example.demo.model.TestXmlDto;
import com.example.demo.service.TestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.xml.bind.JAXBException;
import java.io.*;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@AutoConfigureRestDocs
public class ApiDocumentationJUnit5IntegrationTest {

    @Mock
    private TestService testService;

    @InjectMocks
    private TestController testController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentation) throws JAXBException, FileNotFoundException {

        String respXmlFilePath = "src/test/resources/selectAdminPttnCd.resp.xml";
        TestXmlDto respObjectXml = CustomXmlUtil.getXmlObjectFromFile(TestXmlDto.class, respXmlFilePath);

        Mockito.when(testService.testMethod(any()))
                .thenReturn(respObjectXml);

        this.mockMvc = MockMvcBuilders.standaloneSetup(testController)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .apply(documentationConfiguration(restDocumentation)).build(); // custom??? ??? ??????.
    }

    @Test
    public void testWithXmlFilePayload() throws Exception {

        String reqXmlFilePath = "src/test/resources/selectAdminPttnCd.xml";
        String reqXmlString = CustomXmlUtil.getXmlStringFromFile(TestXmlDto.class, reqXmlFilePath);

        //TODO:[2] xml ????????? ???????????????
        this.mockMvc.perform(post("/test")
                        .content(reqXmlString)                                              // RequestBody(payload)
                        .contentType(MediaType.APPLICATION_XML)                             // Request  ??????
                        .accept(MediaType.APPLICATION_XML)                                  // Response ??????
                )
                .andExpect(status().isOk())                                                 // ???????????? 201??? ??????
                .andDo(document("{class-name}/{method-name}",                   // docs ?????? repo ??????
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),                                  // ??????/????????? ?????? ?????? docs???
                        /* Request ????????? */
                        relaxedRequestFields(                                               // Xml??? verbose?????? ??????
                                subsectionWithPath("Root/Parameters")
                                        .type(JsonFieldType.OBJECT)
                                        .description("?????? ?????? ??????"),
                                subsectionWithPath("Root/Dataset[@id=\"gds_userInfo\"]")    // Section??? ??????
                                        .type(JsonFieldType.OBJECT)                         // ????????? ??????
                                        .description("?????? ?????? ??????"),                        // Section??? ????????? ?????? ??????X
                                fieldWithPath("Root/Dataset[@id=\"ds_cond\"]")              // Field??? ??????
                                        .type(JsonFieldType.OBJECT)                         // ????????? ??????
                                        .description("????????? condition??? ????????? Dataset")       // Field??? ????????? ?????? ??????O
                        ),
                        /* Response ????????? */
                        relaxedResponseFields(
                                fieldWithPath("Root/Dataset").type(JsonFieldType.STRING).description("???????????? ??????")
                        )
                ));
    }

//    private TestService testServiceStub() throws JAXBException, FileNotFoundException {
////        StringWriter stringWriter = new StringWriter();
//        //FIXME: response????????? ????????????.
//        JAXBContext context = JAXBContext.newInstance(TestXmlDto.class);
//        InputStream fileInputStream = new FileInputStream("src/test/resources/selectAdminPttnCd.resp.xml");
//        TestXmlDto responseObjectXml =  (TestXmlDto) context.createUnmarshaller().unmarshal(fileInputStream);
////        context.createMarshaller().marshal(responseObjectXml, stringWriter);
////        String responseStringXml = stringWriter.toString();
//
//        TestService mockService = mock(TestService.class);
//        when(mockService.testMethod(any())).thenReturn(responseObjectXml);
//        return mockService;
//    }


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

//                .alwaysDo(document(
//                        "{class-name}/{method-name}",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint())
//                )).build();
//    @Test
//    public void testWithXmlPayload() throws Exception {
//
//        /* Stub ????????? ????????? ????????? ????????????. */
//        /*
//        List<TestXmlDto.Parameter> parameterList = new ArrayList<>();
//        parameterList.add(new TestXmlDto.Parameter("JSESSIONID", "stub_JSESSIONID"));
//        parameterList.add(new TestXmlDto.Parameter("_xm_webid_1_", "stub_xm_webid"));
//
//        List<TestXmlDto.Dataset> datasetList = new ArrayList<>();
//        datasetList.add(new TestXmlDto.Dataset("id1", "value1"));
//        datasetList.add(new TestXmlDto.Dataset("id2", "value2"));
//         *///??????????????? ???????????? ??? ????????? ?????? ?????? final init ???????????? ??????, Unmarshalling??? ?????? ????????? ????????? ??????.
//
//        TestXmlDto.Parameter[] parameterList = {
//                TestXmlDto.Parameter.builder()
//                        .id("id1")
//                        .value("value1")
//                        .build(),
//                TestXmlDto.Parameter.builder()
//                        .id("id2")
//                        .value("value2")
//                        .build()
//        };
//
//        TestXmlDto.Dataset[] datasetList = {
//                new TestXmlDto.Dataset("id1", "value1"),
//                new TestXmlDto.Dataset("id2", "value2"),
//        };
//
//        TestXmlDto xmlObject = TestXmlDto.builder()
//                .parameters(parameterList)
//                .dataset(datasetList)
//                .build();
//
//        StringWriter stringWriter = new StringWriter();
//        JAXBContext context = JAXBContext.newInstance(TestXmlDto.class);
//        Marshaller marshaller = context.createMarshaller();
//        marshaller.marshal(xmlObject, stringWriter);
//        String xmlPayload = stringWriter.toString();
//
//        this.mockMvc.perform(post("/test/xml")
//                        .contentType(MediaType.APPLICATION_XML)
//                        .content(xmlPayload)
//                        .accept(MediaType.APPLICATION_XML))
//                .andExpect(status().isCreated())
//                .andDo(document("{method-name}",
//                        /* docs??? ????????? sample??? ?????? ?????? ????????????. */
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        /* relaxed[Request/Response]Fields ??? ????????????. */
//                        // ???????????? ????????? ????????? ??? ?????????, xml ????????? ????????? ???????????? ?????? ?????? ??????????????? ?????? ???????????? ????????????.
//                        // Field??? ??????+????????? ?????? ?????? ???, sample payload??? .content(xmlPayload)??? ???????????? ??????????????? ?????? ??????.
//                        relaxedRequestFields(
//                                subsectionWithPath("Root/Dataset").type(JsonFieldType.STRING).description("is dataset section")
//                        ),
//                        /*
//                        relaxedRequestPartFields(
//                                "part", subsectionWithPath("Root/Dataset").type(JsonFieldType.STRING).description("is dataset section")
//                        ),
//                        relaxedRequestFields(beneathPath("Root/Dataset"),
//                                subsectionWithPath("Root/Dataset").type(JsonFieldType.STRING).description("is dataset section")
//                        ),
//                        requestFields(
//                                beneathPath("Root/Dataset"),
////                                subsectionWithPath("Root/Dataset[1]").type(JsonFieldType.STRING).description("is dataset section")
////                                subsectionWithPath("//*").type(JsonFieldType.STRING).description("is dataset section")
//
//                        ),
//                         */// + @????????? advice; relaxed ????????? ?????? ????????? ???????????? ????????? ??? ????????? ??????????????? ?????? above
//                        relaxedResponseFields(
//                                fieldWithPath("Root/Dataset").type(JsonFieldType.STRING).description("???????????? ????????? ????????????.")
//                        )
//                ));
//    }

//    @Test
//    public void testWithXmlStringPayload() throws Exception {
//
//        String xmlString = """
//                <?xml version="1.0" encoding="UTF-8"?>
//                <Root>
//                    <Parameters>
//                        <Parameter id="id1">value1</Parameter>
//                        <Parameter id="id2">value2</Parameter>
//                    </Parameters>
//                    <Dataset id="id1">value1</Dataset>
//                    <Dataset id="id2">value2</Dataset>
//                </Root>""";
//
//        JAXBContext context = JAXBContext.newInstance(TestXmlDto.class);
//        TestXmlDto xmlObject =  (TestXmlDto) context.createUnmarshaller().unmarshal(new StringReader(xmlString));
//
//        StringWriter stringWriter = new StringWriter();
//        Marshaller marshaller = context.createMarshaller();
//        marshaller.marshal(xmlObject, stringWriter);
//        String xmlPayload = stringWriter.toString();
//
//        this.mockMvc.perform(post("/test/xml")
//                        .contentType(MediaType.APPLICATION_XML)
//                        .content(xmlPayload)
//                        .accept(MediaType.APPLICATION_XML))
//                .andExpect(status().isCreated())
//                .andDo(document("{method-name}",
//
//                        /* docs??? ????????? sample??? ?????? ?????? ????????????. */
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//
//                        /* relaxed[Request/Response]Fields ??? ????????????. */
//                        // ???????????? ????????? ????????? ??? ?????????, xml ????????? ????????? ???????????? ?????? ?????? ??????????????? ?????? ???????????? ????????????.
//                        // Field??? ??????+????????? ?????? ?????? ???, sample payload??? .content(xmlPayload)??? ???????????? ??????????????? ?????? ??????.
//                        relaxedRequestFields(
//                                fieldWithPath("Root/Dataset[@id=\"gds_userInfo\"]") .type(JsonFieldType.OBJECT) .description("????????? ????????? ????????? Dataset"),
//
//                                fieldWithPath("Root/Dataset[@id=\"ds_cond\"]")      .type(JsonFieldType.OBJECT) .description("????????? condition??? ????????? Dataset")
//                        ),
//
//                        /*
//                        relaxedRequestPartFields(
//                                "part", subsectionWithPath("Root/Dataset").type(JsonFieldType.STRING).description("is dataset section")
//                        ),
//                        relaxedRequestFields(beneathPath("Root/Dataset"),
//                                subsectionWithPath("Root/Dataset").type(JsonFieldType.STRING).description("is dataset section")
//                        ),
//                        requestFields(
//                                beneathPath("Root/Dataset"),
////                                subsectionWithPath("Root/Dataset[1]").type(JsonFieldType.STRING).description("is dataset section")
////                                subsectionWithPath("//*").type(JsonFieldType.STRING).description("is dataset section")
//
//                        ),
//                         */// + @?????????-advice; relaxed ????????? ?????? ????????? ???????????? ????????? ??? ????????? ??????????????? ?????? above
//
//                        relaxedResponseFields(
//                                fieldWithPath("Root/Dataset").type(JsonFieldType.STRING).description("???????????? ????????? ????????????.")
//                        )
//                ));
//    }

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