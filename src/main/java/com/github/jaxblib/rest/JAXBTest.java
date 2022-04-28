package com.github.jaxblib.rest;

import com.github.jaxblib.commons.jakarta.JakartaMashaller;
import com.github.jaxblib.commons.jaxb.GoodJAXBUtilGeneric;
import com.github.jaxblib.commons.jaxb2.MarshallerJaxb2Wrapper;
import lombok.extern.java.Log;
import org.lfenergy.compas.scl2007b4.model.SCL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@Log
@RequestMapping("/jaxb")
public class JAXBTest {

    private static final String BIG_FILE_BASIC = "PERF/basic-7MB.xml";
    private static final String BIG_FILE_M_10 = "PERF/m10-70MB.xml";

    @Autowired
    private MarshallerJaxb2Wrapper marshallerJaxb2Wrapper;
    @Autowired
    private GoodJAXBUtilGeneric goodJAXBUtilGeneric;

    @Autowired
    private JakartaMashaller jakartaMashaller;

    @GetMapping("/v0/ieds")
    public List<String> testv0() {
        InputStream xmlStream = getClass().getResourceAsStream("/" + BIG_FILE_M_10);
        SCL scl = marshallerJaxb2Wrapper.unmarshall(xmlStream);
        List<String> list = new ArrayList<>();
        scl.getIED().forEach(tied -> list.add(tied.getName()));
        return list;
    }

    @GetMapping("/v1/ieds")
    public List<String> testv1() {
        InputStream xmlStream = getClass().getResourceAsStream("/" + BIG_FILE_M_10);
        SCL scl = goodJAXBUtilGeneric.unmarshal(SCL.class, xmlStream);
        List<String> list = new ArrayList<>();
        scl.getIED().forEach(tied -> list.add(tied.getName()));
        return list;
    }

    @GetMapping("/v2/ieds")
    public List<String> testv2() {
        return new ArrayList<>();
    }

    @GetMapping("/v3/ieds")
    public List<String> testv3() {
        InputStream xmlStream = getClass().getResourceAsStream("/" + BIG_FILE_BASIC);
        SCL scl = jakartaMashaller.unmarshall(SCL.class, xmlStream);
        List<String> list = new ArrayList<>();
        scl.getIED().forEach(tied -> list.add(tied.getName()));
        return list;
    }

}
