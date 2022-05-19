package com.github.jaxblib.commons.jackson;

import com.github.jaxblib.commons.jackson.JacksonSCLImpl;
import com.github.jaxblib.commons.jaxb.JavaSCLJaxbImpl;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.lfenergy.compas.scl2007b4.model.SCL;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
public class JacksonSclTest {

    private static final String SAMPLE = "PERF/test.xml";
    private static final String SAMPLE_FILE = "PERF/basic-7MB.xml";
    private static final String BIG_FILE2 = "PERF/m100-657MB.xml";
    private static final String CURRENT_FILE_TEST = SAMPLE_FILE;

    private final JacksonSCLImpl jacksonSCL = new JacksonSCLImpl();

    @Test
    public void testResourcesAvailable() {
        assertNotNull(getClass().getResource("/" + CURRENT_FILE_TEST),CURRENT_FILE_TEST + " file missing");
    }

    @Test
    @Disabled
    public void testJacksonSCLImplDefault(){
        InputStream xmlStream = getClass().getResourceAsStream("/" + CURRENT_FILE_TEST);

        OutOfMemoryError thrown = assertThrows(OutOfMemoryError.class,
                () -> jacksonSCL.unmarshal(xmlStream), "Expected OutOfMemoryError() to throw, but it didn't");

        thrown.printStackTrace();
        assertTrue(thrown.getMessage().contains("Java heap space"));
    }

    @Test
    public void testJacksonSCLImpl() throws IOException {
        InputStream xmlStream = getClass().getResourceAsStream("/" + CURRENT_FILE_TEST);
        SCL scl = jacksonSCL.unmarshal(xmlStream);
        assertNotNull(scl);
    }

}
