package com.github.jaxblib.service;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.lfenergy.compas.scl2007b4.model.SCL;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class GoodJAXBUtilScl implements JAXBUtil<SCL> {

    @Override
    public String marshal(SCL scl){
        StringWriter stringWriter = new StringWriter();
        try {
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            m.marshal(scl, stringWriter);
            String xml = stringWriter.toString();
            stringWriter.close();
            return xml;
        } catch (JAXBException | IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    @Override
    public SCL unmarshal(String xml) {
        try {
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (SCL)unmarshaller.unmarshal(new StringReader(xml));
        }catch (JAXBException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    @Override
    public SCL unmarshal(InputStream xml) {
        try {
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (SCL)unmarshaller.unmarshal(xml);
        }catch (JAXBException ex) {
            throw new RuntimeException(ex);
        }
    }

    public SCL unmarshalWithSAX(String xml){
        try {
            ByteArrayInputStream input = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
            InputSource inputSource = new InputSource(input);
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
        return "Good";
    }
    
    private static JAXBContext context;
    static{
        try {
            context = JAXBContext.newInstance(SCL.class);
        } catch (JAXBException ex) {
            throw new RuntimeException(ex);
        }
    }
    
}
