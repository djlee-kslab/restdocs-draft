package com.example.demo.controller;

import com.example.demo.model.CrudInput;
import com.example.demo.model.TestDto;
import com.example.demo.model.TestXmlDto;
import com.example.demo.service.TestService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Controller
@RequestMapping("/test")
public class TestController {
//
//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping("/json")
//    public ResponseEntity<TestDto> saveByJson(@RequestBody @Valid TestDto jsonPayload) {
//        return new ResponseEntity<TestDto>(jsonPayload, HttpStatus.CREATED);
//    }

    TestService testService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/xml")
    public ResponseEntity<TestXmlDto> saveByXml(@RequestBody @Valid TestXmlDto xmlPayload) {
        TestXmlDto result = testService.testMethod(xmlPayload);
        return new ResponseEntity<TestXmlDto>(result, HttpStatus.OK);
    }
}