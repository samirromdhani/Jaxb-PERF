package com.github.jaxblib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JaxbDemoApplication implements ApplicationRunner {

	private static Logger LOGGER = LoggerFactory.getLogger(JaxbDemoApplication.class);

	@Override
	public void run(ApplicationArguments args) {
	}

}
