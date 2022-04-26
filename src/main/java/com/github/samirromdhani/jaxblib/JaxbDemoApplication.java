package com.github.samirromdhani.jaxblib;

import com.github.samirromdhani.jaxblib.commons.jakarta.JakartaMashaller;
import com.github.samirromdhani.jaxblib.commons.jaxb.GoodJAXBUtilGeneric;
import lombok.extern.java.Log;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Log
@SpringBootApplication
public class JaxbDemoApplication implements ApplicationRunner {

	public static void main(String[] args) {
		log.info("Jaxb Demo Application starting");
		SpringApplication.run(JaxbDemoApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) {
	}

	@Bean
	public GoodJAXBUtilGeneric goodJAXBUtilGeneric() {
		return new GoodJAXBUtilGeneric();
	}

	@Bean
	public JakartaMashaller jakartaMashaller() { return new JakartaMashaller(); }
}
