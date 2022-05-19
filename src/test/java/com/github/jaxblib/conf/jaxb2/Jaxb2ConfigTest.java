package com.github.jaxblib.conf.jaxb2;

import com.github.jaxblib.conf.RegisterConfigurationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.lfenergy.compas.scl2007b4.model.SCL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.oxm.UnmarshallingFailureException;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { RegisterConfigurationTest.class })
public class Jaxb2ConfigTest {

    private static final String SAMPLE_INVALIDE = "PERF/test-sample-invalid.xml";

    @Autowired
    @Qualifier("defaultJaxb2MarshallerTest")
    private Jaxb2Marshaller defaultJaxb2MarshallerTest ;

    @Autowired
    @Qualifier("customJaxb2MarshallerTest")
    private Jaxb2Marshaller customJaxb2MarshallerTest ;


    @Test
    public void testCreateJaxb2Marshaller() {
        Assertions.assertNotNull(defaultJaxb2MarshallerTest);
        Assertions.assertNotNull(customJaxb2MarshallerTest);
    }

    @Test
    public void testUnmarshal_DefaultJaxb2Marshaller_invalidFile() {
        InputStream xmlStream = getClass().getResourceAsStream("/" + SAMPLE_INVALIDE);
        Source schemaSource = new StreamSource(xmlStream);
        Assertions.assertThrows(UnmarshallingFailureException.class, () -> {
            defaultJaxb2MarshallerTest.unmarshal(schemaSource);
        }, "UnmarshallingFailureException error was expected");
    }

    @Test
    public void testUnmarshal_CustomJaxb2Marshaller_invalidFile() {
        InputStream xmlStream = getClass().getResourceAsStream("/" + SAMPLE_INVALIDE);
        Source schemaSource = new StreamSource(xmlStream);
        SCL scl = (SCL) customJaxb2MarshallerTest.unmarshal(schemaSource);
        Assertions.assertNotNull(scl);
    }
}
