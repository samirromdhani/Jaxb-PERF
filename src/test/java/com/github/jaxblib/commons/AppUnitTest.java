package com.github.jaxblib.commons;

import com.github.jaxblib.commons.compas.MarshallerWrapper;
import com.github.jaxblib.commons.jakarta.JakartaSCLJaxbImpl;
import com.github.jaxblib.commons.jaxb.JavaSCLJaxbImpl;
import com.github.jaxblib.commons.jaxb2.MarshallerJaxb2Wrapper;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.lfenergy.compas.scl2007b4.model.SCL;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @author samirromdhani
 */
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AppUnitTest extends CommonsFile {

    @Autowired
    private JavaSCLJaxbImpl javaSCLJaxb;

    @Autowired
    private JakartaSCLJaxbImpl jakartaSCLJaxb;

    @Autowired
    private MarshallerJaxb2Wrapper marshallerJaxb2Wrapper;

    public MarshallerWrapper marshallerWrapperCompas() {
        return MarshallerWrapper.builder()
                .withProperties("classpath:compas/scl_schema.yml")
                .build();
    }

    private byte[] bytes;

    @BeforeAll
    void init() throws URISyntaxException, IOException {
        long size = getFileFromResource(CURRENT_FILE_TEST).length() ;
        System.out.println(String.format("File size : %,d bytes", size));
        System.out.println(String.format("File size : %,d kilobytes", size / kb));
        System.out.println(String.format("File size : %,d megabytes", size / mb));
        bytes = FileUtils.readFileToByteArray(getFileFromResource(CURRENT_FILE_TEST));
        Assertions.assertNotNull(bytes);
    }

    @Test
    public void testBasicFileJava() throws Exception{
        System.out.println("###########################  Java  ###########################");
        System.gc();
        long premem = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long start = System.currentTimeMillis();
        System.out.println("Used memory pre run (MB): "+(premem/mb));
        System.out.println(javaSCLJaxb.unmarshal(bytes));
        long postmem = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        System.out.println("Used memory post run (MB): "+(postmem/mb));
        System.out.println("Memory consumed (MB): "+(postmem-premem)/mb);
        System.out.println("Time taken in MS: "+(System.currentTimeMillis()-start));
    }

    @Test
    public void testBasicFileJakarta() throws Exception{
        System.out.println("###########################  Jakarta ###########################");
        System.gc();
        long premem = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long start = System.currentTimeMillis();
        System.out.println("Used memory pre run (MB): "+(premem/mb));
        System.out.println(jakartaSCLJaxb.unmarshal(bytes));
        long postmem = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        System.out.println("Used memory post run (MB): "+(postmem/mb));
        System.out.println("Memory consumed (MB): "+(postmem-premem)/mb);
        System.out.println("Time taken in MS: "+(System.currentTimeMillis()-start));
    }

    @Test
    public void testBasicFileMarshallerJaxb2Wrapper() throws Exception{
        System.out.println("###########################  Spring  ###########################");
        System.gc();
        long premem = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long start = System.currentTimeMillis();
        System.out.println("Used memory pre run (MB): "+(premem/mb));
        System.out.println(marshallerJaxb2Wrapper.unmarshal(bytes));
        long postmem = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        System.out.println("Used memory post run (MB): "+(postmem/mb));
        System.out.println("Memory consumed (MB): "+(postmem-premem)/mb);
        System.out.println("Time taken in MS: "+(System.currentTimeMillis()-start));
    }


    @Test
    public void testBasicFilemarshallerWrapperCompas() {
        System.out.println("###########################  CoMPAS SCT  ###########################");
        System.gc();
        long premem = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long start = System.currentTimeMillis();
        System.out.println("Used memory pre run (MB): "+(premem/mb));
        System.out.println(marshallerWrapperCompas().unmarshall(bytes, SCL.class));
        long postmem = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        System.out.println("Used memory post run (MB): "+(postmem/mb));
        System.out.println("Memory consumed (MB): "+(postmem-premem)/mb);
        System.out.println("Time taken in MS: "+(System.currentTimeMillis()-start));
    }


    private File getFileFromResource(String fileName) throws URISyntaxException {

        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return new File(resource.toURI());
        }

    }

}
