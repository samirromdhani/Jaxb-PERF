package com.github.jaxblib.commons.utils;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author samirromdhani
 */
public class ResourceUtils {

    private ResourceUtils() {
    }

    public static InputStream getResourceAsStream(final String fileName) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
    }

    public static String getResourceAsString(final String fileName) throws IOException {
        try (final InputStream inputStream = ResourceUtils.getResourceAsStream(fileName)) {
            return IOUtils.toString(inputStream, "UTF-8");
        }
    }
}
