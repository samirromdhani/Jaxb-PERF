package com.github.jaxblib.service;

import org.apache.commons.io.IOUtils;
import org.lfenergy.compas.scl2007b4.model.SCL;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

public class BadJAXBUtilScl implements JAXBUtil<SCL> {

    @Override
    public String marshal(SCL scl){
        StringWriter stringWriter = new StringWriter();
        try {
            JAXBContext context = JAXBContext.newInstance(SCL.class);
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
            JAXBContext context = JAXBContext.newInstance(SCL.class);
            Unmarshaller u = context.createUnmarshaller();
            byte[] rawXml = IOUtils.resourceToByteArray(xml);
            ByteArrayInputStream input = new ByteArrayInputStream(rawXml);
            return (SCL)u.unmarshal(new StreamSource(input));
        }catch (JAXBException | IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public SCL unmarshal(InputStream xml) {
        try {
            JAXBContext context = JAXBContext.newInstance(SCL.class);
            Unmarshaller u = context.createUnmarshaller();
            return (SCL)u.unmarshal(xml);
        }catch (JAXBException ex) {
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
            JAXBContext context = JAXBContext.newInstance(SCL.class);
            Unmarshaller u = context.createUnmarshaller();

            return (SCL)u.unmarshal(source);
        }catch (ParserConfigurationException | SAXException | JAXBException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    @Override
    public String getName() {
        return "Bad";
    }
    
}
