package com.example.demo.model;

import lombok.Builder;
import lombok.Data;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "Root")
@XmlAccessorType(XmlAccessType.FIELD)
@Builder
@Setter
public class TestXmlDto {

    @XmlElementWrapper(name = "Parameters")
    @XmlElement(name = "Parameter")
    private List<Parameter> parameters;

    @XmlElement(name = "Dataset")
    private String dataset;

    public TestXmlDto() {}

    public TestXmlDto(List<Parameter> parameters, String dataset) {
        this.parameters = parameters;
        this.dataset = dataset;
    }

    @XmlRootElement(name = "Parameter")
    @XmlAccessorType(XmlAccessType.FIELD)
    @Builder
    public static class Parameter {

        //    @Length(min = 2, message = "너무 짧소")
        @XmlAttribute(name = "id")
        private String id;

        @XmlValue
        private String value;

        public Parameter() {}

        public Parameter(String id, String value) {
            this.id = id;
            this.value = value;
        }
    }
}
