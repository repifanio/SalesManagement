package com.camargo.salesmanagement;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SalesManagement {

	final static Logger logger = Logger.getLogger(SalesManagement.class);

	public static void main(String[] args) {
		SpringApplication.run(SalesManagement.class, args);

		logger.info("Aplicação iniciada");
	}

}
