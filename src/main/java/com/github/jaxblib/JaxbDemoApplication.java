package com.github.jaxblib;

import com.github.jaxblib.commons.jackson.JacksonSCLImpl;
import com.github.jaxblib.commons.jakarta.JakartaSCLJaxbImpl;
import com.github.jaxblib.commons.jaxb.JavaJAXBUtilGeneric;
import com.github.jaxblib.commons.jaxb.JavaSCLJaxbImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author samirromdhani
 */
@SpringBootApplication
public class JaxbDemoApplication implements CommandLineRunner {

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
	public JakartaSCLJaxbImpl jakartaSCLJaxb() { return new JakartaSCLJaxbImpl(); }
	@Bean
	public JacksonSCLImpl jacksonSCL() { return new JacksonSCLImpl(); }
}
