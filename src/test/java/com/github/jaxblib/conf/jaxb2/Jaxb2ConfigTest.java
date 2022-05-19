package com.github.jaxblib.conf.jaxb2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.oxm.UnmarshallingFailureException;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;

@SpringBootTest
public class Jaxb2ConfigTest {

    private static final String SAMPLE_INVALIDE = "PERF/test-sample-invalid.xml";

    @Autowired
    private Jaxb2Marshaller defaultJaxb2MarshallerTest ;


    @Test
    public void testCreateJaxb2Marshaller() {
        Assertions.assertNotNull(defaultJaxb2MarshallerTest);
    }

    @Test
    public void testUnmarshal_DefaultJaxb2Marshaller_invalidFile() {
        InputStream xmlStream = getClass().getResourceAsStream("/" + SAMPLE_INVALIDE);
        Source schemaSource = new StreamSource(xmlStream);
        Assertions.assertThrows(UnmarshallingFailureException.class, () -> {
            defaultJaxb2MarshallerTest.unmarshal(schemaSource);
        }, "UnmarshallingFailureException error was expected");
    }

}
