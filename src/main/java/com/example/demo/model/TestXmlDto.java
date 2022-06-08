package com.example.demo.model;

import lombok.Builder;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Root")
@XmlAccessorType(XmlAccessType.FIELD)
@Builder
public class TestXmlDto {

    @Length(min = 2, message = "너무 짧소")
    @XmlElement(name = "Parameters")
    private String parameters;

    @XmlElement(name = "Dataset")
    private String dataset;

    public TestXmlDto() {}

    public TestXmlDto(String parameters, String dataset) {
        this.parameters = parameters;
        this.dataset = dataset;
    }
}