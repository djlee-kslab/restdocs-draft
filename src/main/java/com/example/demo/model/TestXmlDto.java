package com.example.demo.model;

import lombok.*;

import javax.xml.bind.annotation.*;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "Root")
@XmlAccessorType(XmlAccessType.FIELD)
public class TestXmlDto {

    @NonNull
    @XmlElementWrapper(name = "Parameters")
    @XmlElement(name = "Parameter")
    private List<Parameter> parameters;

    @XmlElement(name = "Dataset")
    private String dataset;
//
//    public TestXmlDto(List<Parameter> parameters, String dataset) {
//        this.parameters = parameters;
//        this.dataset = dataset;
//    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @XmlRootElement(name = "Parameter")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Parameter {

        //    @Length(min = 2, message = "너무 짧소")
        @NonNull
        @XmlAttribute(name = "id")
        private String id;

        @NonNull
        @XmlValue
        private String value;
//
//        public Parameter(String id, String value) {
//            this.id = id;
//            this.value = value;
//        }
    }
}
