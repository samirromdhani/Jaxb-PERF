package com.github.jaxblib.commons.jaxb;

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
public class JavaJaxbSclTest {

    private static final String BIG_FILE = "PERF/m10-70MB.xml";
    private static final String BIG_FILE2 = "PERF/m100-657MB.xml";

    private final JavaSCLJaxbImpl  javaSCLJaxb = new JavaSCLJaxbImpl();

    @Test
    public void testResourcesAvailable() {
        assertNotNull(getClass().getResource("/" + BIG_FILE),BIG_FILE + " file missing");
    }

    @Test
    @Disabled
    public void testJavaSCLJaxbImplDefault(){
        InputStream xmlStream = getClass().getResourceAsStream("/" + BIG_FILE);

        OutOfMemoryError thrown = assertThrows(OutOfMemoryError.class,
                () -> javaSCLJaxb.unmarshal(xmlStream), "Expected OutOfMemoryError() to throw, but it didn't");

        thrown.printStackTrace();
        assertTrue(thrown.getMessage().contains("Java heap space"));
    }

    @Test
    public void testJavaSCLJaxbImplSax() throws JAXBException, ParserConfigurationException, SAXException, IOException {
        InputStream xmlStream = getClass().getResourceAsStream("/" + BIG_FILE);
        SCL scl = javaSCLJaxb.unmarshalWithSAX(xmlStream);
        assertNotNull(scl);
    }

}
