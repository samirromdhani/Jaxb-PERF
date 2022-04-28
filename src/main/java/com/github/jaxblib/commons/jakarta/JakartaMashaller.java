package com.github.jaxblib.commons.jakarta;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.lfenergy.compas.scl2007b4.model.SCL;

import java.io.InputStream;

public class JakartaMashaller {

    public <T> T unmarshall(Class<T> tClass, InputStream inputStream) {

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(tClass);
            //JAXBContext jaxbContext = JAXBContextFactory.createContext(new Class[] {tClass}, new HashMap());
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return (T)unmarshaller.unmarshal(inputStream);
            //return tClass.cast(ob);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

}
