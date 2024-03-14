package com.example.PaymentMicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class PaymentMicroserviceApplication {

	public static void main(String[] args){
		new SpringApplicationBuilder(PaymentMicroserviceApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
	}
}
