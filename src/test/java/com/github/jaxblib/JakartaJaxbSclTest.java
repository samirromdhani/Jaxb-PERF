package com.github.jaxblib;

import com.github.jaxblib.commons.jakarta.JakartaSCLJaxbImpl;
import com.github.jaxblib.xsd.jakarta.model.SCL;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;


public class JakartaJaxbSclTest {

    private static final String BIG_FILE = "PERF/m10-70MB.xml";

    private final JakartaSCLJaxbImpl jakartaSCLJaxb = new JakartaSCLJaxbImpl();

    @Test
    public void testResourcesAvailable() {
        assertNotNull(getClass().getResource("/" + BIG_FILE),BIG_FILE + " file missing");
    }

    @Test
    @Disabled
    public void testJakartaSCLJaxbImplDefault(){
        InputStream xmlStream = getClass().getResourceAsStream("/" + BIG_FILE);

        OutOfMemoryError thrown = assertThrows(OutOfMemoryError.class,
                () -> jakartaSCLJaxb.unmarshal(xmlStream), "Expected OutOfMemoryError() to throw, but it didn't");

        thrown.printStackTrace();
        assertTrue(thrown.getMessage().contains("Java heap space"));
    }

    @Test
    public void testJakartaSCLJaxbImplSax() throws ParserConfigurationException, SAXException, jakarta.xml.bind.JAXBException {
        InputStream xmlStream = getClass().getResourceAsStream("/" + BIG_FILE);
        SCL scl = jakartaSCLJaxb.unmarshalWithSAX(xmlStream);
        assertNotNull(scl);
    }

}
