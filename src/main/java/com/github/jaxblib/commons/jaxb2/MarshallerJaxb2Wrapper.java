package com.github.jaxblib.commons.jaxb2;

import com.github.jaxblib.commons.JAXBUtil;
import org.apache.commons.io.input.BOMInputStream;
import org.lfenergy.compas.scl2007b4.model.SCL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author samirromdhani
 */
@Component
public class MarshallerJaxb2Wrapper implements JAXBUtil<SCL> {

    @Autowired
    private Jaxb2Marshaller marshaller;

    @Override
    public byte[] marshal(SCL element) throws IOException {
        StringWriter sw = new StringWriter();
        Result result = new StreamResult(sw);
        marshaller.marshal(element, result);
        return sw.toString().getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public SCL unmarshal(String xml) throws IOException {
        ByteArrayInputStream input = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
        return unmarshal(input);
    }

    @Override
    public SCL unmarshal(InputStream xml) throws IOException {
        Reader reader = new InputStreamReader(new BOMInputStream(xml), "UTF-8");
        return (SCL)marshaller.unmarshal(new StreamSource(reader));
    }

    @Override
    public SCL unmarshal(byte[] bytes) throws IOException {
        ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        return unmarshal(input);
    }

    // TODO
    @Override
    public SCL unmarshalWithSAX(InputSource inputSource) throws ParserConfigurationException, SAXException, JAXBException, jakarta.xml.bind.JAXBException, IOException {
        return null;
    }

    // TODO
    @Override
    public SCL unmarshalWithSAX(InputStream inputStream) throws JAXBException, ParserConfigurationException, SAXException, jakarta.xml.bind.JAXBException, IOException {
        return null;
    }

    @Override
    public String getName() {
        return "Jaxb2";
    }
}
