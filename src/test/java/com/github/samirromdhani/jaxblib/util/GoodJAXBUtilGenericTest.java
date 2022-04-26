package com.github.samirromdhani.jaxblib.util;

import com.github.samirromdhani.jaxblib.commons.jaxb.GoodJAXBUtilGeneric;
import com.github.samirromdhani.jaxblib.commons.scl.SclUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.lfenergy.compas.scl2007b4.model.SCL;
import org.lfenergy.compas.sct.commons.exception.ScdException;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import static org.junit.jupiter.api.Assertions.*;

@Disabled
class GoodJAXBUtilGenericTest {

    private static final String BIG_FILE_BASIC = "PERF/basic-7MB.xml";
    private static final String BIG_FILE_M_10 = "PERF/m10-70MB.xml";
    private final GoodJAXBUtilGeneric goodJAXBUtilGeneric = new GoodJAXBUtilGeneric();

    @Test
    void getData() throws IOException {
        File resource = new ClassPathResource(BIG_FILE_BASIC).getFile();
        String data = new String(
                Files.readAllBytes(resource.toPath()),
                StandardCharsets.UTF_8);
        /*
        assertEquals("...",data);
         */
    }

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
        byte[] data = sclUtils.getData(8);
        GoodJAXBUtilGeneric goodJAXBUtilGeneric = new GoodJAXBUtilGeneric();
        SCL scd = goodJAXBUtilGeneric.unmarshal(SCL.class, data);
        assertEquals(8, scd.getIED().size());
        Files.write(Paths.get("m8.xml"), data);
    }
}