package com.example.demo.controller;

import com.example.demo.model.CrudInput;
import com.example.demo.model.TestDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/test")
public class TestController {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<String> save(@RequestBody TestDto testDto) {
        return new ResponseEntity<String>(testDto.toString(), HttpStatus.CREATED);
    }
}