package com.github.samirromdhani.jaxblib.rest;

import com.github.samirromdhani.jaxblib.commons.compas.MarshallerWrapper;
import com.github.samirromdhani.jaxblib.commons.jaxb.GoodJAXBUtilGeneric;
import com.github.samirromdhani.jaxblib.commons.jaxb2.MarshallerJaxb2Wrapper;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import lombok.extern.java.Log;
import org.lfenergy.compas.scl2007b4.model.SCL;
import org.lfenergy.compas.sct.commons.dto.IedDTO;
import org.lfenergy.compas.sct.commons.exception.ScdException;
import org.lfenergy.compas.sct.commons.scl.SclService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/jaxb")
@Log
public class JAXBTestConsumeXML {

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
            consumes = {MediaType.APPLICATION_XML_VALUE}
    )
    public List<String> testv00(@RequestBody SCL scl) throws ScdException {
        InputStream xmlStream = getClass().getResourceAsStream("/" + BIG_FILE_M_2);
        SCL scd = marshallerJaxb2Wrapper.unmarshall(xmlStream);
        SclService.addIED(scd, "iedName", scl);
        List<String> list = new ArrayList<>();
        scd.getIED().forEach(tied -> list.add(tied.getName()));
        return list;
    }

    @PutMapping(value = "/v1/ieds",
            consumes = {MediaType.APPLICATION_XML_VALUE}
    )
    public List<String> testv01(@RequestBody SCL scl) throws ScdException {
        InputStream xmlStream = getClass().getResourceAsStream("/" + BIG_FILE_M_2);
        SCL scd = goodJAXBUtilGeneric.unmarshal(SCL.class, xmlStream);
        SclService.addIED(scd, "iedName", scl);
        List<String> list = new ArrayList<>();
        scd.getIED().forEach(tied -> list.add(tied.getName()));
        return list;
    }


}
