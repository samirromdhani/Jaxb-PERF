package com.github.jaxblib;

import com.github.jaxblib.commons.jakarta.JakartaSCLJaxbImpl;
import com.github.jaxblib.commons.jaxb.JavaJAXBUtilGeneric;
import com.github.jaxblib.commons.jaxb.JavaSCLJaxbImpl;
import com.github.jaxblib.conf.ConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(ConfigProperties.class)
public class JaxbDemoApplication implements CommandLineRunner {

	@Autowired
	private ConfigProperties configProperties;

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(JaxbDemoApplication.class);
		app.run();
	}

	public void run(String... args) throws Exception {
	}

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
