package com.github.jaxblib.rest;

import com.github.jaxblib.commons.compas.MarshallerWrapper;
import com.github.jaxblib.commons.jakarta.JakartaSCLJaxbImpl;
import com.github.jaxblib.commons.jaxb.JavaSCLJaxbImpl;
import com.github.jaxblib.commons.jaxb2.MarshallerJaxb2Wrapper;
import lombok.extern.java.Log;
import org.lfenergy.compas.scl2007b4.model.SCL;
import org.lfenergy.compas.sct.commons.dto.IedDTO;
import org.lfenergy.compas.sct.commons.exception.ScdException;
import org.lfenergy.compas.sct.commons.scl.SclService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/jaxb")
@Log
public class JAXBTestConsumeFormData {

    /*
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

    @PutMapping(value = "/v0/ieds",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
    )
    public ResponseEntity<?> testv00(@RequestPart(value = "file") MultipartFile file,
                               @RequestPart(value = "data") IedDTO iedDTO) throws IOException, ScdException {
        InputStream xmlStream = getClass().getResourceAsStream("/" + CURRENT_FILE_TEST);
        SCL scd = marshallerJaxb2Wrapper.unmarshall(xmlStream);
        SCL icd = marshallerJaxb2Wrapper.unmarshall(file.getBytes());
        SclService.addIED(scd, iedDTO.getName(), icd);
        byte[] rawXml = marshallerJaxb2Wrapper.marshall(scd).getBytes(StandardCharsets.UTF_8);
        List<String> list = new ArrayList<>();
        scd.getIED().forEach(tied -> list.add(tied.getName()));
        return ResponseEntity.ok().body(list);
    }

    @PutMapping(value = "/v1/ieds",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
    )
    public ResponseEntity<?> testv01(@RequestPart(value = "file") MultipartFile file,
                               @RequestPart(value = "data") IedDTO iedDTO) throws IOException, ScdException, JAXBException, ParserConfigurationException, SAXException {
        InputStream xmlStream = getClass().getResourceAsStream("/" + CURRENT_FILE_TEST);
        SCL scd = javaSCLService.unmarshalWithSAX(xmlStream);
        SCL icd =  javaSCLService.unmarshalWithSAX(file.getInputStream());
        SclService.addIED(scd, iedDTO.getName(), icd);
        byte[] rawXml = javaSCLService.marshal(scd);
        List<String> list = new ArrayList<>();
        scd.getIED().forEach(tied -> list.add(tied.getName()));
        return ResponseEntity.ok().body(list);
    }

    @PutMapping(value = "/v2/ieds",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
    )
    public ResponseEntity<?> testv02(@RequestPart(value = "file") MultipartFile file,
                                     @RequestPart(value = "data") IedDTO iedDTO) throws IOException, ScdException {
        InputStream xmlStream = getClass().getResourceAsStream("/" + CURRENT_FILE_TEST);
        SCL scd = javaSCLService.unmarshal(xmlStream);
        SCL icd = javaSCLService.unmarshal(file.getBytes());
        SclService.addIED(scd, iedDTO.getName(), icd);
        byte[] rawXml = javaSCLService.marshal(scd);
        List<String> list = new ArrayList<>();
        scd.getIED().forEach(tied -> list.add(tied.getName()));
        return ResponseEntity.ok().body(list);
    }

    @PutMapping(value = "/v3/ieds",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
    )
    public ResponseEntity<?> testv3(@RequestPart(value = "file") MultipartFile file,
                                    @RequestPart(value = "data") IedDTO iedDTO) throws IOException, ScdException {
        MarshallerWrapper marshallerWrapper = MarshallerWrapper.builder()
                .withProperties("classpath:scl_schema.yml")
                .build();
        InputStream xmlStream = getClass().getResourceAsStream("/" + CURRENT_FILE_TEST);

        SCL scd = marshallerWrapper.unmarshall(xmlStream, SCL.class);
        SCL icd = marshallerWrapper.unmarshall(file.getBytes(), SCL.class);
        SclService.addIED(scd, iedDTO.getName(), icd);
        byte[] rawXml = marshallerWrapper.marshall(scd).getBytes(StandardCharsets.UTF_8);
        List<String> list = new ArrayList<>();
        scd.getIED().forEach(tied -> list.add(tied.getName()));
        return ResponseEntity.ok().body(list);
    }


    @PutMapping(value = "/v4/ieds",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
    )
    public List<String> testv4(@RequestPart(value = "file") MultipartFile file,
                               @RequestPart(value = "data") IedDTO iedDTO) throws ScdException, IOException {
        // use com.github.jaxblib.xsd.jakarta.model.SCL;
        InputStream xmlStream = getClass().getResourceAsStream("/" + CURRENT_FILE_TEST);
        com.github.jaxblib.xsd.jakarta.model.SCL scd = jakartaSCLJaxb.unmarshal(xmlStream);
        com.github.jaxblib.xsd.jakarta.model.SCL icd = jakartaSCLJaxb.unmarshal(xmlStream);
        //SclService.addIED(scd, iedDTO.getName(), icd);
        byte[] rawXml = jakartaSCLJaxb.marshal(scd);
        List<String> list = new ArrayList<>();
        scd.getIED().forEach(tied -> list.add(tied.getName()));
        return list;
    }
    */
}
