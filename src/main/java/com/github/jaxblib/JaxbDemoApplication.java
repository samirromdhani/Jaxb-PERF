package com.github.jaxblib;

import com.github.jaxblib.commons.jakarta.JakartaSCLJaxbImpl;
import com.github.jaxblib.commons.jaxb.JavaJAXBUtilGeneric;
import com.github.jaxblib.commons.jaxb.JavaSCLJaxbImpl;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

@SpringBootApplication
public class JaxbDemoApplication implements ApplicationRunner {

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

	public static void main(String[] args) {
		SpringApplication.run(JaxbDemoApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) {
	}

	private File getFileFromResource(String fileName) throws URISyntaxException {

		ClassLoader classLoader = getClass().getClassLoader();
		URL resource = classLoader.getResource(fileName);
		if (resource == null) {
			throw new IllegalArgumentException("file not found! " + fileName);
		} else {
			return new File(resource.toURI());
		}

	}

}
