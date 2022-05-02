package com.github.jaxblib;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JaxbDemoApplication implements ApplicationRunner {


	public static void main(String[] args) {
		SpringApplication.run(JaxbDemoApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) {
	}

}
