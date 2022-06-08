package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.Collections;
import java.util.List;

@Getter
public class TestDto {

    @NotNull
    private final   long    id;
    @NotBlank
    private final   String  title;
    private final   String  body;

    @JsonCreator
    public TestDto(     @JsonProperty("id")     long    id,
                        @JsonProperty("title")  String  title,
                        @JsonProperty("body")   String  body
    ) {
        this.id     = id;
        this.title  = title;
        this.body   = body;
    }

    @Override
    public String toString() {
        return String.format("""
                {
                    "id":       %d,
                    "title":    %s,
                    "body":     %s
                }""",
                this.id, this.title, this.body
        );
    }
}