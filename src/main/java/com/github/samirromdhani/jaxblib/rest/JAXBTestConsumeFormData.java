package com.github.samirromdhani.jaxblib.rest;

import com.github.samirromdhani.jaxblib.commons.compas.MarshallerWrapper;
import com.github.samirromdhani.jaxblib.commons.jaxb.GoodJAXBUtilGeneric;
import com.github.samirromdhani.jaxblib.commons.jaxb.GoodJAXBUtilWithoutSAX;
import com.github.samirromdhani.jaxblib.commons.jaxb2.MarshallerJaxb2Wrapper;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
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

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/jaxb")
@Log
public class JAXBTestConsumeFormData {

    private static final String BIG_FILE_BASIC = "PERF/basic-7MB.xml";
    private static final String BIG_FILE_M_2 = "PERF/m2-13.5MB.xml";
    private static final String BIG_FILE_M_4 = "PERF/m4-26.6MB.xml";
    private static final String BIG_FILE_M_8 = "PERF/m8-52.8MB.xml";
    private static final String BIG_FILE_M_10 = "PERF/m10-70MB.xml";
    private static final String CURRENT_FILE_TEST = BIG_FILE_M_10;


    @Autowired
    private MarshallerJaxb2Wrapper marshallerJaxb2Wrapper;
    @Autowired
    private GoodJAXBUtilGeneric goodJAXBUtilGeneric;
    @Autowired
    private GoodJAXBUtilWithoutSAX goodJAXBUtilWithoutSAX;

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
                               @RequestPart(value = "data") IedDTO iedDTO) throws IOException, ScdException {
        InputStream xmlStream = getClass().getResourceAsStream("/" + CURRENT_FILE_TEST);
        SCL scd = goodJAXBUtilGeneric.unmarshal(SCL.class, xmlStream);
        SCL icd = goodJAXBUtilGeneric.unmarshal(SCL.class, file.getBytes());
        SclService.addIED(scd, iedDTO.getName(), icd);
        byte[] rawXml = goodJAXBUtilGeneric.marshal(scd);
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
        SCL scd = goodJAXBUtilWithoutSAX.unmarshal(SCL.class, xmlStream);
        SCL icd = goodJAXBUtilWithoutSAX.unmarshal(SCL.class, file.getBytes());
        SclService.addIED(scd, iedDTO.getName(), icd);
        byte[] rawXml = goodJAXBUtilWithoutSAX.marshal(scd);
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

    /**
     * TODO
     * jakarta
     */
    @PutMapping(value = "/v4/ieds",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
    )
    public List<String> testv4(@RequestPart(value = "file") MultipartFile file,
                               @RequestPart(value = "data") IedDTO iedDTO) throws ScdException, JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(SCL.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        InputStream xmlStream = getClass().getResourceAsStream("/" + CURRENT_FILE_TEST);
        SCL scd = (SCL) unmarshaller.unmarshal(xmlStream);
        SCL icd = (SCL) unmarshaller.unmarshal(xmlStream);
        SclService.addIED(scd, iedDTO.getName(), icd);
        //byte[] rawXml = marshaller.marshal(scd, xmlStream);
        List<String> list = new ArrayList<>();
        scd.getIED().forEach(tied -> list.add(tied.getName()));
        return list;
    }

}
