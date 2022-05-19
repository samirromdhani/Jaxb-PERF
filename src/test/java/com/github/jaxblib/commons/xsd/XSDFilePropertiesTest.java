package com.github.jaxblib.commons.xsd;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class XSDFilePropertiesTest {

    @Autowired
    @Qualifier("xsdFileProperties")
    private XSDFileProperties xsdFileProperties;

    @Test
    void getPaths() {
        assertNotNull(xsdFileProperties.getPaths());
        assertEquals(1,xsdFileProperties.getPaths().size());
    }

    @Test
    void getResources() {
        assertNotNull(xsdFileProperties.getResources());
        assertEquals(1,xsdFileProperties.getResources().size());
    }
}