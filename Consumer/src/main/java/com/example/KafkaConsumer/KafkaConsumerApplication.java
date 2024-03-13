package com.example.KafkaConsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.example.KafkaConsumer.KafkaSubscriber.runConsumer;

@SpringBootApplication
public class KafkaConsumerApplication {

	public static void main(String[] args) throws InterruptedException {
		runConsumer();
	}

}
