package com.github.jaxblib.service;

import org.lfenergy.compas.scl2007b4.model.SCL;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Result;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

public class GoodJaxb2TestUtilSCL implements JAXBUtil<SCL> {

    @Override
    public String marshal(SCL obj) {
        StringWriter sw = new StringWriter();
        Result result = new StreamResult(sw);
        context.marshal(obj, result);
        return sw.toString();
    }

    @Override
    public SCL unmarshal(String xml) {
        ByteArrayInputStream input = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
        return (SCL) context.unmarshal(new StreamSource(input));
    }

    @Override
    public SCL unmarshal(InputStream xml) throws IOException {
        ByteArrayInputStream input = new ByteArrayInputStream(xml.readAllBytes());
        return unmarshal(input.toString());
    }

    @Override
    public SCL unmarshalWithSAX(InputStream xml){
        try {
            InputSource inputSource = new InputSource(xml);
            SAXParserFactory spf = SAXParserFactory.newInstance();
            spf.setNamespaceAware(true);
            spf.setValidating(true);

            SAXParser saxParser = spf.newSAXParser();
            saxParser.setProperty(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);

            XMLReader xmlReader = saxParser.getXMLReader();
            SAXSource source = new SAXSource(xmlReader, inputSource);

            Unmarshaller u = context.createUnmarshaller();

            return (SCL)u.unmarshal(source);
        }catch (ParserConfigurationException | SAXException | JAXBException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public String getName() {
        return "JAXB2";
    }

    private static Jaxb2Marshaller context;
    static{
        context = new Jaxb2Marshaller();
        context.setMarshallerProperties(Collections.singletonMap(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE));
        context.setClassesToBeBound(SCL.class);
    }
}
