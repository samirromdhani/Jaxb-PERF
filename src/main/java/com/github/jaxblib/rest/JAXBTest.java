package com.github.jaxblib.rest;

import com.github.jaxblib.commons.compas.MarshallerWrapper;
import com.github.jaxblib.commons.jaxb.GoodJAXBUtilGeneric;
import com.github.jaxblib.commons.jaxb.GoodJAXBUtilWithoutSAX;
import com.github.jaxblib.commons.jaxb2.MarshallerJaxb2Wrapper;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.lfenergy.compas.scl2007b4.model.SCL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private GoodJAXBUtilGeneric goodJAXBUtilGeneric;
    @Autowired
    private GoodJAXBUtilWithoutSAX goodJAXBUtilWithoutSAX;

    @GetMapping("/v0/ieds")
    public List<String> testv0() {
        InputStream xmlStream = getClass().getResourceAsStream("/" + CURRENT_FILE_TEST);
        SCL scl = marshallerJaxb2Wrapper.unmarshall(xmlStream);
        List<String> list = new ArrayList<>();
        scl.getIED().forEach(tied -> list.add(tied.getName()));
        return list;
    }

    @GetMapping("/v1/ieds")
    public List<String> testv1() {
        InputStream xmlStream = getClass().getResourceAsStream("/" + CURRENT_FILE_TEST);
        SCL scl = goodJAXBUtilGeneric.unmarshal(SCL.class, xmlStream);
        List<String> list = new ArrayList<>();
        scl.getIED().forEach(tied -> list.add(tied.getName()));
        return list;
    }

    @GetMapping("/v2/ieds")
    public List<String> testv2() {
        InputStream xmlStream = getClass().getResourceAsStream("/" + CURRENT_FILE_TEST);
        SCL scl = goodJAXBUtilWithoutSAX.unmarshal(SCL.class, xmlStream);
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

    @GetMapping("/v4/ieds")
    public List<String> testv4() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(SCL.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        InputStream xmlStream = getClass().getResourceAsStream("/" + CURRENT_FILE_TEST);
        SCL scl = (SCL) unmarshaller.unmarshal(xmlStream);
        List<String> list = new ArrayList<>();
        scl.getIED().forEach(tied -> list.add(tied.getName()));
        return list;
    }
}
