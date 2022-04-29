package com.github.jaxblib.commons.jakarta;

import com.github.jaxblib.commons.JAXBUtil;
import com.github.jaxblib.commons.data.Toto;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.Marshaller;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;
import java.io.*;

public class JakartaTotoJaxbImpl implements JAXBUtil<Toto> {

    private static JAXBContext context;
    static{
        try {
            context = JAXBContext.newInstance(Toto.class);
            //context = org.eclipse.persistence.jaxb.JAXBContextFactory.createContext(new Class[]{Toto.class}, null);
        } catch (JAXBException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public byte[] marshal(Toto element) {
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
    public Toto unmarshal(String xml) {
        try {
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (Toto)unmarshaller.unmarshal(new File(xml));
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Toto unmarshal(InputStream inputStream) throws IOException {
        try {
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (Toto)unmarshaller.unmarshal(inputStream);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Toto unmarshal(byte[] bytes) throws IOException {
        try(ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            BufferedInputStream bis = new BufferedInputStream(bais)){
            InputSource inputSource = new InputSource(bis);
            Unmarshaller u = context.createUnmarshaller();
            return (Toto) u.unmarshal(inputSource);
        } catch (IOException | JAXBException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Toto unmarshalWithSAX(InputSource inputSource) throws ParserConfigurationException, SAXException, JAXBException {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        spf.setValidating(true);

        SAXParser saxParser = spf.newSAXParser();
        saxParser.setProperty(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);

        XMLReader xmlReader = saxParser.getXMLReader();
        SAXSource source = new SAXSource( xmlReader, inputSource);

        Unmarshaller u = context.createUnmarshaller();

        return (Toto) u.unmarshal( source );
    }

    @Override
    public Toto unmarshalWithSAX(InputStream inputStream) throws javax.xml.bind.JAXBException, ParserConfigurationException, SAXException, JAXBException {
        InputSource inputSource = new InputSource(inputStream);
        return unmarshalWithSAX(inputSource);
    }

    @Override
    public String getName() {
        return "Jakarta Toto";
    }
}
