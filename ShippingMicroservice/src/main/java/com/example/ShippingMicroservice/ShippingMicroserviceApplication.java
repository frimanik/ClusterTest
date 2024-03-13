package com.example.ShippingMicroservice;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class ShippingMicroserviceApplication {
	public static void main(String[] args) throws InterruptedException {
		new SpringApplicationBuilder(ShippingMicroserviceApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
	}
}
