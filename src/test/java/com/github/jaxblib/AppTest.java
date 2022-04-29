package com.github.jaxblib;

import com.github.jaxblib.commons.jakarta.JakartaSCLJaxbImpl;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import static org.junit.Assert.assertTrue;

public class AppTest
{
    private final long mb = 1024*1024;
    private static final String BASIC_FILE = "PERF/basic-7MB.xml";
    private static final String BIG_FILE = "PERF/m10-70MB.xml";

    private final JakartaSCLJaxbImpl jakartaSCLJaxb = new JakartaSCLJaxbImpl();

    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @EventListener(ApplicationReadyEvent.class)
    @Test
    @Disabled
    public void testLargeFile() throws Exception{
        long premem = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long start = System.currentTimeMillis();

        System.out.println("Used memory pre run (MB): "+(premem/mb));
        System.out.println(jakartaSCLJaxb.unmarshal(FileUtils.readFileToByteArray(getFileFromResource(BASIC_FILE))));
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
