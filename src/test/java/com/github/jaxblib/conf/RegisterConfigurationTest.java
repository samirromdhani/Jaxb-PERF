package com.github.jaxblib.conf;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import com.github.jaxblib.commons.jaxb2.JaxbConfiguration;
import com.github.jaxblib.commons.jaxbV2.JaxbV2Impl;
import com.github.jaxblib.commons.xsd.XSDFileProperties;
import org.lfenergy.compas.scl2007b4.model.SCL;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.ParserConfigurationException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Configuration
@ComponentScan({ "com.github.jaxblib.commons.*" })
@EnableConfigurationProperties(XSDFileProperties.class)
public class RegisterConfigurationTest {

    @Bean
    public JaxbConfiguration getJaxbConfiguration() {
        return Mockito.mock(JaxbConfiguration.class);
    }

    @Bean
    @Primary
    @Qualifier("defaultJaxb2MarshallerTest")
    public Jaxb2Marshaller getDefaultJaxb2MarshallerTest(ResourceLoader resourceLoader, XSDFileProperties xsdFileProperties) throws Exception {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setMarshallerProperties(Collections.singletonMap(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE));
        jaxb2Marshaller.setClassesToBeBound(SCL.class);
        List<Resource> resources = new ArrayList<>();
        if(xsdFileProperties != null) {

            for (Map.Entry<String, String> path : xsdFileProperties.getPaths().entrySet()) {
                String filePath = path.getValue();
                resources.add(resourceLoader.getResource(filePath));
            }

        } else {
            Resource resource = resourceLoader.getResource("classpath:/xsd/SCL2007B4/SCL.xsd");
            if(resource.exists())
                resources.add(resource);
        }

        if(!resources.isEmpty()) {
            jaxb2Marshaller.setSchemas(resources.toArray(Resource[]::new));
        }
        jaxb2Marshaller.afterPropertiesSet();

        return jaxb2Marshaller;
    }

    @Bean
    @Qualifier("customJaxb2MarshallerTest")
    public Jaxb2Marshaller getCustomJaxb2MarshallerTest() throws Exception {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setMarshallerProperties(Collections.singletonMap(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE));
        jaxb2Marshaller.setClassesToBeBound(SCL.class);
        jaxb2Marshaller.afterPropertiesSet();
        return jaxb2Marshaller;
    }

    @Bean
    @Qualifier("defaultXmlMapper")
    public XmlMapper getDefaultXmlMapper() {
        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        XmlMapper xmlMapper = new XmlMapper(module);
        xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // JAXB annotations
        xmlMapper.registerModule(new JaxbAnnotationModule());
        return xmlMapper;
    }

    @Bean
    @Qualifier("xmlMapperII")
    public XmlMapper getXmlMapperII() {
        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        XmlMapper xmlMapper = new XmlMapper(module);
        xmlMapper.setAnnotationIntrospector(new JaxbAnnotationIntrospector());
        xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // JAXB annotations
        xmlMapper.registerModule(new JaxbAnnotationModule());
        return xmlMapper;
    }

    @Bean
    @Qualifier("xmlMapperIII")
    public ObjectMapper getXmlMapperIII() {
        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        ObjectMapper xmlMapper = new XmlMapper(module);
        xmlMapper.setAnnotationIntrospector(new JaxbAnnotationIntrospector());
        xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // JAXB annotations
        xmlMapper.registerModule(new JaxbAnnotationModule());
        return xmlMapper;
    }

    @Bean
    @Qualifier("jaxbV2Impl")
    public JaxbV2Impl getJaxbV2Impl() throws JAXBException, ParserConfigurationException, SAXException {
        JaxbV2Impl jaxbV2 = new JaxbV2Impl();
        return jaxbV2;
    }

}
