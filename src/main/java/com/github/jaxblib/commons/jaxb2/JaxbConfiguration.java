package com.github.jaxblib.commons.jaxb2;

import com.github.jaxblib.commons.xsd.XSDFileProperties;
import org.lfenergy.compas.scl2007b4.model.SCL;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.xml.bind.Marshaller;
import java.util.Collections;

/**
 * @author samirromdhani
 */
@Configuration
@EnableConfigurationProperties(XSDFileProperties.class)
public class JaxbConfiguration {

    @Bean
    public Jaxb2Marshaller jaxb2Marshaller(XSDFileProperties xsdFileProperties) throws Exception {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setMarshallerProperties(Collections.singletonMap(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE));
        jaxb2Marshaller.setClassesToBeBound(SCL.class);
        jaxb2Marshaller.setSchemas(xsdFileProperties.getResources().toArray(Resource[]::new));
        jaxb2Marshaller.afterPropertiesSet();
        return jaxb2Marshaller;
    }

    @Bean
    @Qualifier("customJaxb2Marshaller")
    public Jaxb2Marshaller getCustomJaxb2Marshaller(ResourceLoader resourceLoader, XSDFileProperties xsdFileProperties) throws Exception {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setMarshallerProperties(Collections.singletonMap(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE));
        jaxb2Marshaller.setClassesToBeBound(SCL.class);
        jaxb2Marshaller.afterPropertiesSet();
        return jaxb2Marshaller;
    }
}
