package com.github.jaxblib.commons.jakarta;

import com.github.jaxblib.commons.CommonsFile;
import com.github.jaxblib.xsd.jakarta.model.SCL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JakartaJaxbSclTest extends CommonsFile {

    @Autowired
    private JakartaSCLJaxbImpl jakartaSCLJaxb;

    @Test
    public void testCreateJakartaSCLJaxbImpl() {
        Assertions.assertNotNull(jakartaSCLJaxb);
    }

    @Test
    public void testUnmarshal_JavaSCLJaxb_validFile() throws Exception {
        InputStream xmlStream = getClass().getResourceAsStream("/" + SAMPLE);
        SCL scl = jakartaSCLJaxb.unmarshal(xmlStream);
        Assertions.assertNotNull(scl);
    }

    @Test
    public void testValidate_JavaSCLJaxb_validFile() throws Exception {
        InputStream xmlStream = getClass().getResourceAsStream("/" + SAMPLE_INVALIDE);
        Assertions.assertThrows(RuntimeException.class, () -> {
            jakartaSCLJaxb.unmarshal(xmlStream);
        }, "RuntimeException error was expected");
    }

    @Test
    public void testJakartaSCLJaxbImplSax() throws ParserConfigurationException, SAXException, jakarta.xml.bind.JAXBException {
        InputStream xmlStream = getClass().getResourceAsStream("/" + BIG_FILE);
        SCL scl = jakartaSCLJaxb.unmarshalWithSAX(xmlStream);
        assertNotNull(scl);
    }

}
