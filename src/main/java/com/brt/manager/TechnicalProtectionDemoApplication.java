package com.brt.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.*"})
public class TechnicalProtectionDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechnicalProtectionDemoApplication.class, args);
	}

}
