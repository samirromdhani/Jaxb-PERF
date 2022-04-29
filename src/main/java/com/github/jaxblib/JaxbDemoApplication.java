package com.github.jaxblib;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

@SpringBootApplication
public class JaxbDemoApplication implements ApplicationRunner {

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
