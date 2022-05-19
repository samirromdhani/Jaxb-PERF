package com.github.jaxblib.conf.jaxbV2;

import com.github.jaxblib.commons.jaxbV2.JaxbV2Impl;
import com.github.jaxblib.conf.RegisterConfigurationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { RegisterConfigurationTest.class })
public class JaxbV2ConfigTest {

    private static final String EMPTY = "PERF/test-empty.xml";
    private static final String SAMPLE = "PERF/test-sample.xml";
    private static final String SAMPLE_INVALIDE = "PERF/test-sample-invalid.xml";
    private static final String SAMPLE_FILE = "PERF/basic-7MB.xml";
    private static final String SAMPLE_10_FILE = "PERF/m10-70MB-test.xml";
    private static final String BIG_FILE = "PERF/large-test.xml";
    private static final String CURRENT_FILE_TEST = SAMPLE_INVALIDE;

    @Autowired
    @Qualifier("jaxbV2Impl")
    private JaxbV2Impl jaxbV2Impl ;


    @Test
    public void testCreateJaxbV2Impl() {
        Assertions.assertNotNull(jaxbV2Impl);
    }

    @Test
    public void testUnmarshal_DefaultJaxb2Marshaller_invalidFile() throws IOException {
        InputStream xmlStream = getClass().getResourceAsStream("/" + CURRENT_FILE_TEST);
        Source schemaSource = new StreamSource(xmlStream);
        File targetFile = new File("src/test/resources/"+CURRENT_FILE_TEST);
        //jaxbV2Impl.testParser(new String(xmlStream.readAllBytes(), StandardCharsets.UTF_8));
        //jaxbV2Impl.testParser(targetFile.toURL().toExternalForm());
        assertDoesNotThrow(() -> jaxbV2Impl.testParser(targetFile.toURL().toExternalForm()));
    }
}
