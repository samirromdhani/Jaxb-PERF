package com.github.jaxblib;

import com.github.jaxblib.commons.data.Toto;
import com.github.jaxblib.commons.jakarta.JakartaTotoJaxbImpl;
import com.github.jaxblib.commons.xsd.XsdUtil;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.logging.Level;

@Log
@Disabled
public class JakartaTotoTest {
    
    @Test
    public void testMarshal() {
        Toto toto = new Toto();
        JakartaTotoJaxbImpl jakartaTotoJaxb = new JakartaTotoJaxbImpl();
        String xml = new String(jakartaTotoJaxb.marshal(toto));
        Assertions.assertEquals(xml.trim(),marshalOutput.trim());
        log.info(xml);
    }
    
    @Test
    public void testUnmarshal() throws IOException {
        JakartaTotoJaxbImpl jakartaTotoJaxb = new JakartaTotoJaxbImpl();
        Toto toto = jakartaTotoJaxb.unmarshal(marshalOutput.getBytes());
        Assertions.assertNotNull(toto);
        log.log(Level.INFO, "{0}", toto);
    }
    
    @Test
    public void testGetXSD() {
        XsdUtil xsdUtil = new XsdUtil();
        String xsd = xsdUtil.getXsd(Toto.class);
        Assertions.assertEquals(xsd.trim(),xsdOutput.trim());
        log.severe(xsd);
    }
    
    private static final String marshalOutput = "<toto id=\"0\"/>";
    
    private static final String xsdOutput = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<xs:schema version=\"1.0\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">\n" +
            "\n" +
            "  <xs:complexType name=\"toto\">\n" +
            "    <xs:sequence>\n" +
            "      <xs:element name=\"field\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
            "      <xs:element name=\"id\" type=\"xs:int\"/>\n" +
            "    </xs:sequence>\n" +
            "  </xs:complexType>\n" +
            "</xs:schema>";
}
