package com.github.jaxblib.conf.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github.jaxblib.conf.RegisterConfigurationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.lfenergy.compas.scl2007b4.model.SCL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.io.*;
import java.util.Arrays;
import java.util.stream.Collectors;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { RegisterConfigurationTest.class })
public class JacksonConfigTest {

    private static final String EMPTY = "PERF/test-empty.xml";
    private static final String SAMPLE = "PERF/test-sample.xml";
    private static final String SAMPLE_FILE = "PERF/basic-7MB.xml";
    private static final String SAMPLE_10_FILE = "PERF/m10-70MB-test.xml";
    private static final String BIG_FILE = "PERF/large-test.xml";
    private static final String CURRENT_FILE_TEST = SAMPLE;

    @Autowired
    @Qualifier("defaultXmlMapper")
    private XmlMapper defaultXmlMapper ;

    @Autowired
    @Qualifier("xmlMapperII")
    private XmlMapper xmlMapperII ;

    @Autowired
    @Qualifier("xmlMapperIII")
    private ObjectMapper xmlMapperIII ;

    @Test
    public void testCreateXmlMapper() {
        Assertions.assertNotNull(defaultXmlMapper);
    }

    @Test
    public void testUnmarshal_DefaultXmlMapper() throws IOException {
        InputStream xmlStream = getClass().getResourceAsStream("/" + CURRENT_FILE_TEST);
        SCL scl = defaultXmlMapper.readValue(xmlStream, SCL.class);
        Assertions.assertNotNull(scl);
        Assertions.assertEquals(2,scl.getIED().size());
        Assertions.assertEquals(Arrays.asList("IED0","IED1"),scl.getIED().stream().map(tied -> tied.getName())
                .collect(Collectors.toList()));
    }

    @Test
    public void testUnmarshal_XmlMapperII() throws IOException {
        InputStream xmlStream = getClass().getResourceAsStream("/" + CURRENT_FILE_TEST);
        SCL scl = xmlMapperII.readValue(xmlStream, SCL.class);
        Assertions.assertNotNull(scl);
        Assertions.assertEquals(Arrays.asList("IED0","IED1"),scl.getIED().stream().map(tied -> tied.getName())
                .collect(Collectors.toList()));
    }

    @Test
    public void testUnmarshal_XmlMapperIII() throws IOException {
        InputStream xmlStream = getClass().getResourceAsStream("/" + CURRENT_FILE_TEST);
        SCL scl = xmlMapperIII.readValue(xmlStream, SCL.class);
        Assertions.assertNotNull(scl);
        Assertions.assertEquals(Arrays.asList("IED0","IED1"),scl.getIED().stream().map(tied -> tied.getName())
                .collect(Collectors.toList()));
    }

    @Test
    public void testMarshal_DefaultXmlMapper() throws IOException {
        InputStream xmlStream = getClass().getResourceAsStream("/" + CURRENT_FILE_TEST);
        SCL scl = defaultXmlMapper.readValue(xmlStream, SCL.class);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        defaultXmlMapper.writeValue(byteArrayOutputStream, scl);
        //Assertions.assertEquals(null, new String( byteArrayOutputStream.toByteArray()));
    }

    @Test
    public void testMarshal_XmlMapperII() throws IOException {
        InputStream xmlStream = getClass().getResourceAsStream("/" + CURRENT_FILE_TEST);
        SCL scl = xmlMapperII.readValue(xmlStream, SCL.class);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        xmlMapperII.writeValue(byteArrayOutputStream, scl);
        //Assertions.assertEquals(null, new String( byteArrayOutputStream.toByteArray()));
    }
}
