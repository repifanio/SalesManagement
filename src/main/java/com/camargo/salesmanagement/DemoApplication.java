package com.camargo.salesmanagement;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
// @EnableScheduling
public class DemoApplication {

	final static Logger logger = Logger.getLogger(DemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		System.out.println("Teste de app sem Schedulle");

		if (logger.isDebugEnabled()) {
			logger.error("This is debug");
		}
	}

}
