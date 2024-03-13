package com.example.OrdersMicroservice;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProduceMessage {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @PostConstruct
    public void init() {
        try {
            sendMessage();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage() throws InterruptedException {
        int i = 0;
        while (true) {
            kafkaTemplate.send("new_orders", "new Order: " + i);
            i++;
            Thread.sleep(5000);
        }
    }
}
