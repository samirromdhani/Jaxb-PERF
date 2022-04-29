package com.github.jaxblib.conf;

import com.github.jaxblib.commons.jakarta.JakartaSCLJaxbImpl;
import com.github.jaxblib.commons.jaxb.JavaJAXBUtilGeneric;
import com.github.jaxblib.commons.jaxb.JavaSCLJaxbImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public JavaJAXBUtilGeneric goodJAXBUtilGeneric() {
        return new JavaJAXBUtilGeneric();
    }

    @Bean
    public JavaSCLJaxbImpl javaSCLJaxb() {
        return new JavaSCLJaxbImpl();
    }

    @Bean
    public JakartaSCLJaxbImpl jakartaSCLJaxb() {
        return new JakartaSCLJaxbImpl();
    }

}
