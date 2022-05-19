package com.github.jaxblib;

import com.github.jaxblib.rest.JAXBUnmarshalTest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.io.IOException;
import java.io.UnsupportedEncodingException;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = JaxbDemoApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("spring")
@Tag("java")
@Tag("compas")
@Tag("jakarta")
@Disabled
public class AppRestTest
{
    private final long mb = 1024*1024;

    private static Logger log = LoggerFactory.getLogger(AppRestTest.class);

    @Autowired
    private JAXBUnmarshalTest jaxbUnmarshalTest;

    @BeforeEach
    void initGc(){
        System.gc();
    }

    @BeforeAll
    void init(){
        log.debug("### Init test report with 70 MB Unmarshalling ###");
        Assertions.assertNotNull(jaxbUnmarshalTest);
    }

    @Test
    @Tag("spring")
    public void testJaxbSpring() throws UnsupportedEncodingException {
        log.debug("###########################      Jaxb2 Spring    ###########################");
        long premem = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long start1 = System.currentTimeMillis();
        // log.debug("Used memory pre run (MB): "+(premem/mb));
        jaxbUnmarshalTest.testv0();
        long postmem = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        // log.debug("Used memory post run (MB): "+(postmem/mb));
        log.debug("Memory consumed (MB): "+(postmem-premem)/mb);
        log.debug("Time taken in MS: "+(System.currentTimeMillis()-start1));
    }

    @Test
    @Tag("java")
    public void testJaxb2_3() throws IOException {
        log.debug("###########################  Java 2.3.1 (Javax)  ###########################");
        long premem = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long start = System.currentTimeMillis();
        // log.debug("Used memory pre run (MB): "+(premem/mb));
        jaxbUnmarshalTest.testv1().size();
        long postmem = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        // log.debug("Used memory post run (MB): "+(postmem/mb));
        log.debug("Memory consumed (MB): "+(postmem-premem)/mb);
        log.debug("Time taken in MS: "+(System.currentTimeMillis()-start));
    }

    @Test
    @Tag("compas")
    public void testJaxbCompas() {
        log.debug("###########################  Java (Javax Compas SCT)  ###########################");
        long premem = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long start = System.currentTimeMillis();
        // log.debug("Used memory pre run (MB): "+(premem/mb));
        jaxbUnmarshalTest.testv3();
        long postmem = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        // log.debug("Used memory post run (MB): "+(postmem/mb));
        log.debug("Memory consumed (MB): "+(postmem-premem)/mb);
        log.debug("Time taken in MS: "+(System.currentTimeMillis()-start));
    }

    @Test
    @Tag("jakarta")
    public void testJaxbJakarta() throws IOException {
        log.debug("###########################  Java 3.0.0 (jakarta)  ###########################");
        long premem = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long start = System.currentTimeMillis();
        // log.debug("Used memory pre run (MB): "+(premem/mb));
        jaxbUnmarshalTest.testv4();
        long postmem = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        // log.debug("Used memory post run (MB): "+(postmem/mb));
        log.debug("Memory consumed (MB): "+(postmem-premem)/mb);
        log.debug("Time taken in MS: "+(System.currentTimeMillis()-start));
    }

}
