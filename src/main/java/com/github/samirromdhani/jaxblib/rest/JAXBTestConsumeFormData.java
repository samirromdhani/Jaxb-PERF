package com.github.samirromdhani.jaxblib.rest;

import com.github.samirromdhani.jaxblib.commons.compas.MarshallerWrapper;
import com.github.samirromdhani.jaxblib.commons.jaxb.GoodJAXBUtilGeneric;
import com.github.samirromdhani.jaxblib.commons.jaxb2.MarshallerJaxb2Wrapper;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.lfenergy.compas.scl2007b4.model.SCL;
import org.lfenergy.compas.sct.commons.Utils;
import org.lfenergy.compas.sct.commons.dto.IedDTO;
import org.lfenergy.compas.sct.commons.exception.ScdException;
import org.lfenergy.compas.sct.commons.scl.SclService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/jaxb")
@Log
public class JAXBTestConsumeFormData {

    private static final String BIG_FILE_BASIC = "PERF/basic-7MB.xml";
    private static final String BIG_FILE_M_2 = "PERF/m2-13.5MB.xml";
    private static final String BIG_FILE_M_4 = "PERF/m4-26.6MB.xml";
    private static final String BIG_FILE_M_8 = "PERF/m8-52.8MB.xml";
    private static final String BIG_FILE_M_10 = "PERF/m10-70MB.xml";

    @Autowired
    private MarshallerJaxb2Wrapper marshallerJaxb2Wrapper;
    @Autowired
    private GoodJAXBUtilGeneric goodJAXBUtilGeneric;

    @PutMapping(value = "/v0/ieds",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
    )
    public List<String> testv00(@RequestPart(value = "file") MultipartFile file,
                               @RequestPart(value = "data") IedDTO iedDTO) throws IOException, ScdException {
        InputStream xmlStream = getClass().getResourceAsStream("/" + BIG_FILE_BASIC);
        SCL scd = marshallerJaxb2Wrapper.unmarshall(xmlStream);
        SCL icd = marshallerJaxb2Wrapper.unmarshall(file.getBytes());
        SclService.addIED(scd, iedDTO.getName(), icd);
        List<String> list = new ArrayList<>();
        scd.getIED().forEach(tied -> list.add(tied.getName()));
        return list;
    }

    @PutMapping(value = "/v1/ieds",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
    )
    public List<String> testv01(@RequestPart(value = "file") MultipartFile file,
                               @RequestPart(value = "data") IedDTO iedDTO) throws IOException, ScdException {
        InputStream xmlStream = getClass().getResourceAsStream("/" + BIG_FILE_BASIC);
        SCL scd = goodJAXBUtilGeneric.unmarshal(SCL.class, xmlStream);
        SCL icd = goodJAXBUtilGeneric.unmarshal(SCL.class, file.getBytes());
        SclService.addIED(scd, iedDTO.getName(), icd);
        List<String> list = new ArrayList<>();
        scd.getIED().forEach(tied -> list.add(tied.getName()));
        return list;
    }

    @PutMapping(value = "/v2/ieds",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
    )
    public List<String> testv2() {
        MarshallerWrapper marshallerWrapper = MarshallerWrapper.builder()
                .withProperties("classpath:scl_schema.yml")
                .build();

        InputStream xmlStream = getClass().getResourceAsStream("/" + BIG_FILE_BASIC);
        SCL scl = marshallerWrapper.unmarshall(xmlStream, SCL.class);
        List<String> list = new ArrayList<>();
        scl.getIED().forEach(tied -> list.add(tied.getName()));
        return list;
    }

    @PutMapping(value = "/v3/ieds",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
    )
    public List<String> testv3() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(SCL.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        InputStream xmlStream = getClass().getResourceAsStream("/" + BIG_FILE_BASIC);
        SCL scl = (SCL) unmarshaller.unmarshal(xmlStream);
        List<String> list = new ArrayList<>();
        scl.getIED().forEach(tied -> list.add(tied.getName()));
        return list;
    }
}
