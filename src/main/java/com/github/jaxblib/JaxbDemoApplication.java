package com.github.jaxblib;

import com.github.jaxblib.commons.jaxb.JavaJAXBUtilGeneric;
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

}
