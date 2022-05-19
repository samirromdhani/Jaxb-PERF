package com.github.jaxblib.conf;

import com.github.jaxblib.commons.jaxb2.JaxbConfiguration;
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
import javax.xml.bind.Marshaller;
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

}
