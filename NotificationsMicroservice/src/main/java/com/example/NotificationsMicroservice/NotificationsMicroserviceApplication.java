package com.example.NotificationsMicroservice;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class NotificationsMicroserviceApplication {

	public static void main(String[] args) throws InterruptedException {
		new SpringApplicationBuilder(NotificationsMicroserviceApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
	}
}
