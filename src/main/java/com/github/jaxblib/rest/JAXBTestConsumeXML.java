package com.github.jaxblib.rest;

import com.github.jaxblib.commons.compas.MarshallerWrapper;
import com.github.jaxblib.commons.jakarta.JakartaSCLJaxbImpl;
import com.github.jaxblib.commons.jaxb.JavaSCLJaxbImpl;
import com.github.jaxblib.commons.jaxb2.MarshallerJaxb2Wrapper;
import jakarta.xml.bind.JAXBException;
import lombok.extern.java.Log;
import org.lfenergy.compas.scl2007b4.model.SCL;
import org.lfenergy.compas.sct.commons.exception.ScdException;
import org.lfenergy.compas.sct.commons.scl.SclService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author samirromdhani
 */
@RestController
@RequestMapping("/jaxb")
@Log
public class JAXBTestConsumeXML {

    private static final String BIG_FILE_BASIC = "PERF/basic-7MB.xml";
    private static final String BIG_FILE_M_2 = "PERF/m2-13.5MB.xml";
    private static final String BIG_FILE_M_4 = "PERF/m4-26.6MB.xml";
    private static final String BIG_FILE_M_8 = "PERF/m8-52.8MB.xml";
    private static final String BIG_FILE_M_10 = "PERF/m10-70MB.xml";
    private static final String CURRENT_FILE_TEST = BIG_FILE_M_10;

    @Autowired
    private MarshallerJaxb2Wrapper marshallerJaxb2Wrapper;
    @Autowired
    private JavaSCLJaxbImpl javaSCLService;
    @Autowired
    private JakartaSCLJaxbImpl jakartaSCLJaxb;

    @PutMapping(value = "/v000/ieds",
            consumes = {MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<?> testv00(@RequestBody SCL icd) throws ScdException, UnsupportedEncodingException {
        InputStream xmlStream = getClass().getResourceAsStream("/" + CURRENT_FILE_TEST);
        SCL scd = marshallerJaxb2Wrapper.unmarshall(xmlStream);
        //SCL icd = marshallerJaxb2Wrapper.unmarshall(file.getBytes());
        SclService.addIED(scd, "iedName", icd);
        byte[] rawXml = marshallerJaxb2Wrapper.marshall(scd).getBytes(StandardCharsets.UTF_8);
        List<String> list = new ArrayList<>();
        scd.getIED().forEach(tied -> list.add(tied.getName()));
        return ResponseEntity.ok().body(list);
    }

    @PutMapping(value = "/v001/ieds",
            consumes = {MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<?> testv01(@RequestBody SCL scl) throws ScdException, IOException {
        InputStream xmlStream = getClass().getResourceAsStream("/" + CURRENT_FILE_TEST);
        SCL scd = javaSCLService.unmarshal(xmlStream);
        SclService.addIED(scd, "iedName", scl);
        List<String> list = new ArrayList<>();
        byte[] rawXml = javaSCLService.marshal(scd);
        scd.getIED().forEach(tied -> list.add(tied.getName()));
        return ResponseEntity.ok().body(list);
    }

    @PutMapping(value = "/v002/ieds",
            consumes = {MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<?> testv02(@RequestBody SCL scl) throws ScdException, JAXBException, ParserConfigurationException, SAXException, javax.xml.bind.JAXBException, IOException {
        InputStream xmlStream = getClass().getResourceAsStream("/" + CURRENT_FILE_TEST);
        SCL scd = javaSCLService.unmarshalWithSAX(xmlStream);
        SclService.addIED(scd, "iedName", scl);
        List<String> list = new ArrayList<>();
        byte[] rawXml = javaSCLService.marshal(scd);
        scd.getIED().forEach(tied -> list.add(tied.getName()));
        return ResponseEntity.ok().body(list);
    }

    @PutMapping(value = "/v003/ieds",
            consumes = {MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<?> testv03(@RequestBody SCL scl) throws ScdException {
        MarshallerWrapper marshallerWrapper = MarshallerWrapper.builder()
                .withProperties("classpath:scl_schema.yml")
                .build();
        InputStream xmlStream = getClass().getResourceAsStream("/" + CURRENT_FILE_TEST);
        SCL scd = marshallerWrapper.unmarshall(xmlStream, SCL.class);
        SclService.addIED(scd, "iedName", scl);
        List<String> list = new ArrayList<>();
        byte[] rawXml = marshallerWrapper.marshall(scd).getBytes(StandardCharsets.UTF_8);
        scd.getIED().forEach(tied -> list.add(tied.getName()));
        return ResponseEntity.ok().body(list);
    }

    /**
     * use com.github.jaxblib.xsd.jakarta.model.SCL;
     */
    @PutMapping(value = "/v004/ieds",
            consumes = {MediaType.APPLICATION_XML_VALUE}
    )
    public List<String> testv04(@RequestBody SCL icd) throws ScdException, IOException {
        InputStream xmlStream = getClass().getResourceAsStream("/" + CURRENT_FILE_TEST);
        com.github.jaxblib.xsd.jakarta.model.SCL scd = jakartaSCLJaxb.unmarshal(xmlStream);
        //com.github.jaxblib.xsd.jakarta.model.SCL icd = jakartaSCLJaxb.unmarshal(xmlStream);
        //SclService.addIED(scd, "ied1", icd);
        byte[] rawXml = jakartaSCLJaxb.marshal(scd);
        List<String> list = new ArrayList<>();
        scd.getIED().forEach(tied -> list.add(tied.getName()));
        return list;
    }


}
