package com.github.samirromdhani.jaxblib;

import com.github.samirromdhani.jaxblib.data.MyJAXBObject;
import com.github.samirromdhani.jaxblib.commons.jaxb.GoodJAXBUtilGeneric;
import com.github.samirromdhani.jaxblib.commons.xsd.XsdUtil;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.logging.Level;

@Log
public class JaxbUtilTest {
    
    @Test
    public void testMarshal() {
        MyJAXBObject myJAXBObject = new MyJAXBObject();
        myJAXBObject.setId(1);
        myJAXBObject.setField("some field");

        GoodJAXBUtilGeneric jaxbUtil = new GoodJAXBUtilGeneric();
        String xml = new String(jaxbUtil.marshal(myJAXBObject));
        
        Assertions.assertEquals(xml.trim(),marshalOutput.trim());
        log.severe(xml);
    }
    
    @Test
    public void testUnmarshal() {
        GoodJAXBUtilGeneric jaxbUtil = new GoodJAXBUtilGeneric();
        MyJAXBObject myJAXBObject = jaxbUtil.unmarshal(MyJAXBObject.class,marshalOutput.getBytes());
        
        Assertions.assertNotNull(myJAXBObject);
        log.log(Level.SEVERE, "{0}", myJAXBObject);
    }
    
    @Test
    public void testGetXSD() {
        XsdUtil xsdUtil = new XsdUtil();
        String xsd = xsdUtil.getXsd(MyJAXBObject.class);
        Assertions.assertEquals(xsd.trim(),xsdOutput.trim());
        log.severe(xsd);
    }
    
    private static final String marshalOutput = "<myJAXBObject id=\"1\">\n" +
                                                "    <field>some field</field>\n" +
                                                "</myJAXBObject>";
    
    private static final String xsdOutput = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                                            "<xs:schema version=\"1.0\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">\n" +
                                            "\n" +
                                            "  <xs:element name=\"myJAXBObject\" type=\"myJAXBObject\"/>\n" +
                                            "\n" +
                                            "  <xs:complexType name=\"myJAXBObject\">\n" +
                                            "    <xs:sequence>\n" +
                                            "      <xs:element name=\"field\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                                            "    </xs:sequence>\n" +
                                            "    <xs:attribute name=\"id\" type=\"xs:int\" use=\"required\"/>\n" +
                                            "  </xs:complexType>\n" +
                                            "</xs:schema>";
}
