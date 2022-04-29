package com.github.jaxblib.rest;

import com.github.jaxblib.commons.compas.MarshallerWrapper;
import com.github.jaxblib.commons.jakarta.JakartaSCLJaxbImpl;
import com.github.jaxblib.commons.jaxb.JavaSCLJaxbImpl;
import com.github.jaxblib.commons.jaxb2.MarshallerJaxb2Wrapper;
import org.lfenergy.compas.scl2007b4.model.SCL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/jaxb")
public class JAXBTest {

    private static final String BIG_FILE_BASIC = "PERF/basic-7MB.xml";
    private static final String BIG_FILE_M_10 = "PERF/m10-70MB.xml";
    private static final String CURRENT_FILE_TEST = BIG_FILE_M_10;

    @Autowired
    private MarshallerJaxb2Wrapper marshallerJaxb2Wrapper;
    @Autowired
    private JavaSCLJaxbImpl javaSCLJaxb;
    @Autowired
    private JakartaSCLJaxbImpl jakartaSCLJaxb;

    @GetMapping("/v0/ieds")
    public List<String> testv0() {
        InputStream xmlStream = getClass().getResourceAsStream("/" + CURRENT_FILE_TEST);
        SCL scl = marshallerJaxb2Wrapper.unmarshall(xmlStream);
        List<String> list = new ArrayList<>();
        scl.getIED().forEach(tied -> list.add(tied.getName()));
        return list;
    }

    @GetMapping("/v1/ieds")
    public List<String> testv1() throws IOException {
        InputStream xmlStream = getClass().getResourceAsStream("/" + CURRENT_FILE_TEST);
        SCL scl = javaSCLJaxb.unmarshal(xmlStream);
        List<String> list = new ArrayList<>();
        scl.getIED().forEach(tied -> list.add(tied.getName()));
        return list;
    }

    @GetMapping("/v2/ieds")
    public List<String> testv2() throws JAXBException, ParserConfigurationException, SAXException {
        InputStream xmlStream = getClass().getResourceAsStream("/" + CURRENT_FILE_TEST);
        SCL scl = javaSCLJaxb.unmarshalWithSAX(xmlStream);
        List<String> list = new ArrayList<>();
        scl.getIED().forEach(tied -> list.add(tied.getName()));
        return list;
    }


    @GetMapping("/v3/ieds")
    public List<String> testv3() {
        MarshallerWrapper marshallerWrapper = MarshallerWrapper.builder()
                .withProperties("classpath:scl_schema.yml")
                .build();

        InputStream xmlStream = getClass().getResourceAsStream("/" + CURRENT_FILE_TEST);
        SCL scl = marshallerWrapper.unmarshall(xmlStream, SCL.class);
        List<String> list = new ArrayList<>();
        scl.getIED().forEach(tied -> list.add(tied.getName()));
        return list;
    }

    /**
     * use com.github.jaxblib.xsd.jakarta.model.SCL;
     */
    @GetMapping("/v4/ieds")
    public List<String> testv4() throws IOException {
        InputStream xmlStream = getClass().getResourceAsStream("/" + CURRENT_FILE_TEST);
        com.github.jaxblib.xsd.jakarta.model.SCL scl = jakartaSCLJaxb.unmarshal(xmlStream);
        List<String> list = new ArrayList<>();
        scl.getIED().forEach(tied -> list.add(tied.getName()));
        return list;
    }

    /**
     * use com.github.jaxblib.xsd.jakarta.model.SCL;
     */
    @GetMapping("/v5/ieds")
    public List<String> testv5() throws IOException, jakarta.xml.bind.JAXBException, ParserConfigurationException, SAXException {
        InputStream xmlStream = getClass().getResourceAsStream("/" + CURRENT_FILE_TEST);
        com.github.jaxblib.xsd.jakarta.model.SCL scl = jakartaSCLJaxb.unmarshalWithSAX(xmlStream);
        List<String> list = new ArrayList<>();
        scl.getIED().forEach(tied -> list.add(tied.getName()));
        return list;
    }
}
