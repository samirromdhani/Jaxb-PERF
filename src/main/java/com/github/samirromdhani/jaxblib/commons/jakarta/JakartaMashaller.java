package com.github.samirromdhani.jaxblib.commons.jakarta;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import java.io.InputStream;

public class JakartaMashaller {
    public <T> T unmarshall(Class<T> tClass, InputStream inputStream) {

        try {
            JAXBContext jaxbContext = JakartaContextHolder.getJAXBContext(tClass);
            //JAXBContext jaxbContext = JAXBContextFactory.createContext(new Class[] {tClass.getClass()}, null);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Object ob = unmarshaller.unmarshal(inputStream);
            return tClass.cast(ob);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

}
