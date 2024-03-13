package com.example.OrdersMicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class OrdersMicroserviceApplication {

	public static void main(String[] args) throws InterruptedException {
		new SpringApplicationBuilder(OrdersMicroserviceApplication.class)
				.web(WebApplicationType.NONE) // .REACTIVE, .SERVLET
				.run(args);
	}

}
