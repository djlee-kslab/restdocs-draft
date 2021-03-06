package com.example.demo.model;

import lombok.*;

import javax.xml.bind.annotation.*;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "Root")
//@XmlRootElement(name = "Root", namespace = "http://www.nexacroplatform.com/platform/dataset")
@XmlAccessorType(XmlAccessType.FIELD)
public class TestXmlDto {

    @XmlAttribute
    private String xmlns;

    @NonNull
    @XmlElementWrapper(name = "Parameters")
    @XmlElement(name = "Parameter")
    private Parameter[] parameters;

    @XmlElement(name = "Dataset")
    private Dataset[] datasets;

    /*
    public TestXmlDto(List<Parameter> parameters, String dataset) {
        this.parameters = parameters;
        this.dataset = dataset;
    }
     */// Unmarshalling이 잘 되지 않는 경우 직접 작성해줘야 할 수도 있다.

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

        /*
        public Parameter(String id, String value) {
            this.id = id;
            this.value = value;
        }

         */// Unmarshalling이 잘 되지 않는 경우 직접 작성해줘야 할 수도 있다. (2)
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @XmlRootElement(name = "Dataset")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Dataset {

        //    @Length(min = 2, message = "너무 짧소")
        @NonNull
        @XmlAttribute(name = "id")
        private String id;

        @NonNull
        @XmlValue
        private String value;

        /*
        public Parameter(String id, String value) {
            this.id = id;
            this.value = value;
        }

         */// Unmarshalling이 잘 되지 않는 경우 직접 작성해줘야 할 수도 있다. (2)
    }
}
