package com.github.jaxblib.commons.jakarta;

import com.github.jaxblib.commons.JAXBUtil;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.*;

import com.github.jaxblib.xsd.jakarta.model.SCL;
// import org.lfenergy.compas.scl2007b4.model.SCL;

public class JakartaSCLJaxbImpl implements JAXBUtil<SCL> {

    String xsdFile = "target/classes/SCL2007B4/SCL.xsd";
    private static JAXBContext context;
    static{
        try {
            context = JAXBContext.newInstance(SCL.class);
            //context = org.eclipse.persistence.jaxb.JAXBContextFactory.createContext(new Class[]{SCL.class}, null);
        } catch (JAXBException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public byte[] marshal(SCL element) {
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(baos)){
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            m.marshal(element, bos);
            bos.flush();
            return baos.toByteArray();
        } catch (IOException | JAXBException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public SCL unmarshal(String xml) {
        try {
            Unmarshaller unmarshaller = context.createUnmarshaller();
            //Setup schema validator
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = sf.newSchema(new File(xsdFile));
            unmarshaller.setSchema(schema);
            return (SCL)unmarshaller.unmarshal(new File(xml));
        } catch (JAXBException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SCL unmarshal(InputStream inputStream) throws IOException {
        try {
            Unmarshaller unmarshaller = context.createUnmarshaller();
            //Setup schema validator
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = sf.newSchema(new File(xsdFile));
            unmarshaller.setSchema(schema);
            return (SCL)unmarshaller.unmarshal(inputStream);
        } catch (JAXBException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SCL unmarshal(byte[] bytes) throws IOException {
        try(ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            BufferedInputStream bis = new BufferedInputStream(bais)){
            return unmarshal(bis);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public SCL unmarshalWithSAX(InputSource inputSource) throws ParserConfigurationException, SAXException, JAXBException {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        spf.setValidating(true);

        SAXParser saxParser = spf.newSAXParser();
        saxParser.setProperty(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);

        XMLReader xmlReader = saxParser.getXMLReader();
        SAXSource source = new SAXSource( xmlReader, inputSource);

        Unmarshaller unmarshaller = context.createUnmarshaller();

        //Setup schema validator
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = sf.newSchema(new File(xsdFile));
        unmarshaller.setSchema(schema);

        return (SCL)unmarshaller.unmarshal( source );
    }

    @Override
    public SCL unmarshalWithSAX(InputStream inputStream) throws JAXBException, ParserConfigurationException, SAXException {
        InputSource inputSource = new InputSource(inputStream);
        return unmarshalWithSAX(inputSource);
    }

    @Override
    public String getName() {
        return "Jakarta SCL";
    }
}
