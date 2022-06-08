package com.example.demo.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "root")
public class TestXmlDto {

    @XmlElement(name = "Parameter")
    private String parameter;

    @XmlElement(name = "Dataset")
    private String dataset;
}