package com.github.jaxblib.commons.jaxb;

import com.github.jaxblib.commons.CommonsFile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.lfenergy.compas.scl2007b4.model.SCL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JavaJaxbSclTest extends CommonsFile {

    @Autowired
    private JavaSCLJaxbImpl javaSCLJaxb;

    @Test
    public void testCreateJavaSCLJaxbImpl() {
        Assertions.assertNotNull(javaSCLJaxb);
    }

    @Test
    public void testUnmarshal_JavaSCLJaxb_validFile() throws Exception {
        InputStream xmlStream = getClass().getResourceAsStream("/" + SAMPLE);
        SCL scl = javaSCLJaxb.unmarshal(xmlStream);
        Assertions.assertNotNull(scl);
    }

    @Test
    public void testValidate_JavaSCLJaxb_validFile() throws Exception {
        InputStream xmlStream = getClass().getResourceAsStream("/" + SAMPLE_INVALIDE);
        Assertions.assertThrows(RuntimeException.class, () -> {
            javaSCLJaxb.unmarshal(xmlStream);
        }, "RuntimeException error was expected");
    }

    @Test
    public void testJavaSCLJaxbImplSax() throws JAXBException, ParserConfigurationException, SAXException, IOException {
        InputStream xmlStream = getClass().getResourceAsStream("/" + BIG_FILE);
        SCL scl = javaSCLJaxb.unmarshalWithSAX(xmlStream);
        assertNotNull(scl);
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

}
