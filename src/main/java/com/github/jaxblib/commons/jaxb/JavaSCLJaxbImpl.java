package com.github.jaxblib.commons.jaxb;

import com.github.jaxblib.commons.JAXBUtil;
import com.github.jaxblib.commons.utils.ValidatorUtils;
import org.lfenergy.compas.scl2007b4.model.SCL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
import java.io.*;

/**
 * @author samirromdhani
 */
@Component
public class JavaSCLJaxbImpl implements JAXBUtil<SCL> {


    @Autowired
    private ValidatorUtils validatorUtils;

    private static JAXBContext context;
    static{
        try {
            context = JAXBContext.newInstance(SCL.class);
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
        } catch (JAXBException | IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public SCL unmarshal(String xml) {
        try {
            Unmarshaller unmarshaller = context.createUnmarshaller();
            unmarshaller.setSchema(validatorUtils.getSchema());
            return (SCL) unmarshaller.unmarshal(new File(xml));
        } catch (JAXBException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SCL unmarshal(InputStream inputStream) throws IOException {
        try {
            Unmarshaller unmarshaller = context.createUnmarshaller();
            unmarshaller.setSchema(validatorUtils.getSchema());
            return (SCL) unmarshaller.unmarshal(inputStream);
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
    public SCL unmarshalWithSAX(InputSource inputSource) throws ParserConfigurationException, SAXException, JAXBException, IOException {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        spf.setValidating(true);

        SAXParser saxParser = spf.newSAXParser();
        saxParser.setProperty(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);

        XMLReader xmlReader = saxParser.getXMLReader();
        SAXSource source = new SAXSource( xmlReader, inputSource);

        Unmarshaller unmarshaller = context.createUnmarshaller();
        unmarshaller.setSchema(validatorUtils.getSchema());
        return (SCL)unmarshaller.unmarshal( source );
    }

    @Override
    public SCL unmarshalWithSAX(InputStream inputStream) throws JAXBException, ParserConfigurationException, SAXException, IOException {
        InputSource inputSource = new InputSource(inputStream);
        return unmarshalWithSAX(inputSource);
    }

    @Override
    public String getName() {
        return "Java SCL";
    }
    
}
