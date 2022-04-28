package com.github.jaxblib.service;
import java.io.IOException;
import java.io.InputStream;

public interface JAXBUtil<T extends Object> {
    String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
    String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
    String marshal(T element);
    T unmarshal(String xml);
    T unmarshal(InputStream xml) throws IOException;
    T unmarshalWithSAX(InputStream xml);
    String getName();
}
