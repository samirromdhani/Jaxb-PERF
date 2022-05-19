package com.github.jaxblib.commons;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author samirromdhani
 */
public interface JAXBUtil<T extends Object> {
    byte[] marshal(T element) throws IOException;
    T unmarshal(String xml) throws JsonProcessingException;
    T unmarshal(InputStream xml) throws IOException;
    T unmarshal(byte[] bytes) throws IOException;

    // SAX Utils
    String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
    String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
    T unmarshalWithSAX(InputSource inputSource) throws ParserConfigurationException, SAXException, JAXBException, jakarta.xml.bind.JAXBException, IOException;
    T unmarshalWithSAX(InputStream inputStream) throws JAXBException, ParserConfigurationException, SAXException, jakarta.xml.bind.JAXBException, IOException;

    String getName();
}
