package com.github.jaxblib.commons.jaxbV2;

import com.github.jaxblib.commons.JAXBUtil;
//import org.lfenergy.compas.scl2007b4.model.SCL;
import com.github.jaxblib.xsd.jakarta.model.SCL;
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
 * Created on 2022-05-17
 *
 * @author samirromdhani
 */
public class JaxbV2Impl implements JAXBUtil<SCL> {

    private static JAXBContext context;
    private static XMLReader reader;
    static{
        try {
            String of = org.lfenergy.compas.scl2007b4.model.ObjectFactory.class.getPackage().getName();
            String customOF = com.github.jaxblib.xsd.jakarta.model.ObjectFactory.class.getPackage().getName();
            context = JAXBContext.newInstance(customOF);
            //Jaxb2Marshaller context = new Jaxb2Marshaller();
            //context.setPackagesToScan("","");
            //SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            //Schema schema = sf.newSchema(new File(xsdFile));
            //context.createUnmarshaller().setSchema(schema);
            // create a new XML parser
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setNamespaceAware(true);
            reader = factory.newSAXParser().getXMLReader();
            Splitter splitter = new Splitter(context);
            reader.setContentHandler(splitter);
        } catch (JAXBException ex) {
            throw new RuntimeException(ex);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
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

    public void testParser(String xml) {
        try {
            reader.parse(xml);
        } catch (IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    public void testParser(File file) {
        try {
            reader.parse(file.toURL().toExternalForm());
        } catch (IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SCL unmarshal(String xml) {
        try {
            Unmarshaller unmarshaller = context.createUnmarshaller();
            //TODO Setup schema validator
            return (SCL) unmarshaller.unmarshal(new File(xml));
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SCL unmarshal(InputStream inputStream) throws IOException {
        try {
            Unmarshaller unmarshaller = context.createUnmarshaller();
            //TODO Setup schema validator
            return (SCL) unmarshaller.unmarshal(inputStream);
        } catch (JAXBException e) {
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
        //TODO Setup schema validator
        return (SCL)unmarshaller.unmarshal(source);
    }

    @Override
    public SCL unmarshalWithSAX(InputStream inputStream) throws JAXBException, ParserConfigurationException, SAXException, IOException {
        InputSource inputSource = new InputSource(inputStream);
        return unmarshalWithSAX(inputSource);
    }

    @Override
    public String getName() {
        return "JaxbV2 SCL";
    }
}
