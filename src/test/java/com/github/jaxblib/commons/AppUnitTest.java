package com.github.jaxblib.commons;

import com.github.jaxblib.commons.jakarta.JakartaSCLJaxbImpl;
import com.github.jaxblib.commons.jaxb.JavaSCLJaxbImpl;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public class AppUnitTest
{
    private final long mb = 1024*1024;
    private static final String BASIC_FILE = "PERF/basic-7MB.xml";
    private static final String BIG_FILE = "PERF/m10-70MB.xml";
    private static final String BIG_FILE2 = "PERF/m100-657MB.xml";
    private static final String CURRENT_FILE_TEST = BIG_FILE;


    private final JakartaSCLJaxbImpl jakartaSCLJaxb = new JakartaSCLJaxbImpl();
    private final JavaSCLJaxbImpl javaSCLJaxb = new JavaSCLJaxbImpl();

    @EventListener(ApplicationReadyEvent.class)
    @Test
    public void testBasicFileJava() throws Exception{
        System.out.println("###########################  Java  ###########################");
        System.gc();
        long premem1 = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long start1 = System.currentTimeMillis();
        System.out.println("Used memory pre run (MB): "+(premem1/mb));
        System.out.println(javaSCLJaxb.unmarshal(FileUtils.readFileToByteArray(getFileFromResource(CURRENT_FILE_TEST))));
        long postmem1 = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        System.out.println("Used memory post run (MB): "+(postmem1/mb));
        System.out.println("Memory consumed (MB): "+(postmem1-premem1)/mb);
        System.out.println("Time taken in MS: "+(System.currentTimeMillis()-start1));
    }

    @EventListener(ApplicationReadyEvent.class)
    @Test
    public void testBasicFileJakarta() throws Exception{
        System.out.println("###########################  Jakarta ###########################");
        System.gc();
        long premem2 = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long start2 = System.currentTimeMillis();
        System.out.println("Used memory pre run (MB): "+(premem2/mb));
        System.out.println(jakartaSCLJaxb.unmarshal(FileUtils.readFileToByteArray(getFileFromResource(CURRENT_FILE_TEST))));
        long postmem2 = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        System.out.println("Used memory post run (MB): "+(postmem2/mb));
        System.out.println("Memory consumed (MB): "+(postmem2-premem2)/mb);
        System.out.println("Time taken in MS: "+(System.currentTimeMillis()-start2));
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
