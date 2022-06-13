package com.example.demo;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.StringWriter;

public class CustomXmlUtil {

    public static <T> T getXmlObjectFromFile(Class<T> tClass, String filePath) throws JAXBException, FileNotFoundException {

        JAXBContext context = JAXBContext.newInstance(tClass);
        InputStream fileInputStream = new FileInputStream(filePath);

        return (tClass.cast(context.createUnmarshaller().unmarshal(fileInputStream)));
    }
}
