package com.camargo.salesmanagement;

import com.camargo.salesmanagement.controller.Reader;
import com.camargo.salesmanagement.services.ScheduledTasks;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.config.ScheduledTask;

@SpringBootApplication
// @EnableScheduling
public class DemoApplication {

	final static Logger logger = Logger.getLogger(DemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

		ScheduledTasks task = new ScheduledTasks();
		task.reportCurrentTime();

	}

}
