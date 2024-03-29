package com.github.jaxblib.util;

import com.github.jaxblib.commons.jaxb.JavaJAXBUtilGeneric;
import com.github.jaxblib.commons.scl.SclUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.lfenergy.compas.scl2007b4.model.SCL;
import org.lfenergy.compas.sct.commons.exception.ScdException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import static org.junit.jupiter.api.Assertions.*;

@Disabled
class SclUtilTest {

    private static final String BIG_FILE_BASIC = "PERF/basic-7MB.xml";
    private static final String BIG_FILE_M_10 = "PERF/m10-70MB.xml";
    private final JavaJAXBUtilGeneric goodJAXBUtilGeneric = new JavaJAXBUtilGeneric();

    @Test
    public void testJAXBUtilGeneric(){
        InputStream xmlStream = getClass().getResourceAsStream("/" + BIG_FILE_BASIC);
        final long startmarshal = System.currentTimeMillis();
        SCL scl = goodJAXBUtilGeneric.unmarshal(SCL.class, xmlStream);
        final long timemarshal = System.currentTimeMillis() - startmarshal;
        System.out.println("BIG_FILE_BASIC Marshal took: "+ timemarshal + " ms");
        assertNotNull(scl);

        InputStream xmlStream2 = getClass().getResourceAsStream("/" + BIG_FILE_M_10);
        final long startmarshal2 = System.currentTimeMillis();
        SCL scl2 = goodJAXBUtilGeneric.unmarshal(SCL.class, xmlStream2);
        final long timemarshal2 = System.currentTimeMillis() - startmarshal2;
        System.out.println("BIG_FILE_M_10 Marshal took: "+ timemarshal2 + " ms");
        assertNotNull(scl2);
    }
    /**
     * generate new data
     */
    public static void main(String args[]) throws ScdException, IOException {
        SclUtils sclUtils = new SclUtils();
        byte[] data = sclUtils.getData(100);
        JavaJAXBUtilGeneric goodJAXBUtilGeneric = new JavaJAXBUtilGeneric();
        SCL scd = goodJAXBUtilGeneric.unmarshal(SCL.class, data);
        assertEquals(100, scd.getIED().size());
        Files.write(Paths.get("m100-657MB.xml"), data);
    }
}