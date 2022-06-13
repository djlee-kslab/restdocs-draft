package com.example.demo.controller;

import com.example.demo.model.TestXmlDto;
import com.example.demo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Validated
@RestController
@RequestMapping("/test")
public class TestController {

    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

//    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<TestXmlDto> save(@RequestBody @Valid TestXmlDto payload) {
        TestXmlDto result = testService.testMethod(payload);
        return new ResponseEntity<TestXmlDto>(result, HttpStatus.OK);
    }
}