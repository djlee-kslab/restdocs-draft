package com.example.demo;

import com.example.demo.controller.TestController;
import com.example.demo.model.TestDto;
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
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(document(
                        "{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                )).build();

    }

    @Test
    public void indexExample() throws Exception {
        TestDto payload = new TestDto(2L, "Test title", "Test body");
        this.mockMvc.perform(post("/test")
                        .contentType(MediaType.APPLICATION_XML)
                    .content(this.objectMapper.writeValueAsString(payload)))
                .andExpect(status().isCreated())
                .andDo(document("{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
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
//        this.mockMvc.perform(get("/"))
//                .andExpect(status().isOk())
//                .andDo(document("index-example",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        links(
//                                linkWithRel("crud").description("The CRUD resource")
//                        ),
//                        responseFields(subsectionWithPath("_links").description("Links to other resources")),
//                        responseHeaders(headerWithName("Content-Type").description("The Content-Type of the payload, e.g. `application/hal+json`"))
//                ));
//    }
//
//        this.mockMvc.perform(post("/crud")
//                        .contentType(MediaTypes.HAL_JSON)
//                        .content(this.objectMapper.writeValueAsString(crud)))
//                .andExpect(status().isCreated())
//                .andDo(document("crud-create-example",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        requestFields(
//                                fieldWithPath("id").description("The id of the input"),
//                                fieldWithPath("title").description("The title of the input"),
//                                fieldWithPath("body").description("The body of the input"),
//                                fieldWithPath("tags").description("An array of tag resource URIs")
//                        )
//                ));



//    @Test
//    public void indexExample() throws Exception {
//        this.mockMvc.perform(get("/"))
//                .andExpect(status().isOk())
//                .andDo(document("index-example",
////                        preprocessRequest(prettyPrint()),
////                        preprocessResponse(prettyPrint()),
//                        links(linkWithRel("crud").description("The CRUD resource")),
//                        responseFields(
//                                subsectionWithPath("_links").description("Links to other resources")
//                        ),
//                        responseHeaders(
//                                headerWithName("Content-Type").description("The Content-Type of the payload, e.g. `application/hal+json`")
//                        ))
//                );
////                        links(linkWithRel("crud").description("The CRUD resource")),
////                        responseFields(subsectionWithPath("_links")
////                                .description("Links to other resources"))
////                        responseHeaders(headerWithName("Content-Type")
////                                .description("The Content-Type of the payload"))));
//    }

//    @Test
//    public void contextLoads() {
//    }
}
