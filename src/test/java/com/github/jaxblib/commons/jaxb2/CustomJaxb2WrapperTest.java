package com.github.jaxblib.commons.jaxb2;

import com.github.jaxblib.AppRestTest;
import com.github.jaxblib.conf.RegisterConfigurationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.lfenergy.compas.scl2007b4.model.SCL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.UnmarshallingFailureException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.InputStream;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { RegisterConfigurationTest.class })
class CustomJaxb2WrapperTest {

    private static final String EMPTY = "PERF/test-empty.xml";
    private static final String SAMPLE = "PERF/test-sample.xml";
    private static final String SAMPLE_INVALIDE = "PERF/test-sample-invalid.xml";
    private static final String SAMPLE_FILE = "PERF/basic-7MB.xml";
    private static final String SAMPLE_10_FILE = "PERF/m10-70MB-test.xml";
    private static final String BIG_FILE = "PERF/large-test.xml";
    private static final String CURRENT_FILE_TEST = SAMPLE_10_FILE;

    private final long mb = 1024*1024;

    private static Logger log = LoggerFactory.getLogger(AppRestTest.class);

    @Autowired
    private CustomJaxb2Wrapper customJaxb2Wrapper ;

    @Test
    public void testCreateJaxb2Marshaller() {
        Assertions.assertNotNull(customJaxb2Wrapper);
    }

    @Test
    public void testUnmarshal_CustomJaxb2Marshaller_validFile() throws Exception {
        InputStream xmlStream = getClass().getResourceAsStream("/" + CURRENT_FILE_TEST);
        SCL scl = customJaxb2Wrapper.unmarshal(xmlStream, false);
        Assertions.assertNotNull(scl);
    }

    @Test
    public void testValidate_CustomJaxb2Marshaller_validFile() throws Exception {
        InputStream xmlStream = getClass().getResourceAsStream("/" + SAMPLE_INVALIDE);
        Assertions.assertThrows(UnmarshallingFailureException.class, () -> {
                    customJaxb2Wrapper.unmarshal(xmlStream, true);
                }, "UnmarshallingFailureException error was expected");
    }

    ///Perf test
    @Test
    public void test1_CustomJaxb2Marshaller_validFile() throws Exception {
        log.debug("###########################   Jaxb2 Unmarshal without validation    ###########################");
        InputStream xmlStream = getClass().getResourceAsStream("/" + CURRENT_FILE_TEST);
        long premem = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long start1 = System.currentTimeMillis();
        log.debug("Used memory pre run (MB): "+(premem/mb));
        SCL scl = customJaxb2Wrapper.unmarshal(xmlStream, false);
        Assertions.assertNotNull(scl);
        long postmem = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        log.debug("Used memory post run (MB): "+(postmem/mb));
        log.debug("Memory consumed (MB): "+(postmem-premem)/mb);
        log.debug("Time taken in MS: "+(System.currentTimeMillis()-start1));
    }

    @Test
    public void test2_CustomJaxb2Marshaller_validFile() throws Exception {
        log.debug("###########################   Jaxb2 Unmarshal with validation    ###########################");
        InputStream xmlStream = getClass().getResourceAsStream("/" + CURRENT_FILE_TEST);
        long premem = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long start1 = System.currentTimeMillis();
        log.debug("Used memory pre run (MB): "+(premem/mb));
        SCL scl1 = customJaxb2Wrapper.unmarshal(xmlStream, true);
        Assertions.assertNotNull(scl1);
        long postmem = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        log.debug("Used memory post run (MB): "+(postmem/mb));
        log.debug("Memory consumed (MB): "+(postmem-premem)/mb);
        log.debug("Time taken in MS: "+(System.currentTimeMillis()-start1));
    }

}