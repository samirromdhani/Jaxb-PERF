package com.github.samirromdhani.jaxblib.commons.jaxb;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class JaxbContextHolder {

    private static final Map<String, JAXBContext> CONTEXT_MAP = new ConcurrentHashMap<>();

    public static JAXBContext getJAXBContext(Object ob) {
        final Class<?> c = ob.getClass();
        return getJAXBContext(c);
    }

    public static JAXBContext getJAXBContext(Class<?> c) {
        final String name = c.getName();
        if (CONTEXT_MAP.containsKey(name)) {
            return CONTEXT_MAP.get(name);
        } else {
            try {
                JAXBContext context = JAXBContext.newInstance(c);
                CONTEXT_MAP.put(name, context);
                return context;
            } catch (JAXBException e) {
                throw new RuntimeException(e);
            }
        }
    }
}