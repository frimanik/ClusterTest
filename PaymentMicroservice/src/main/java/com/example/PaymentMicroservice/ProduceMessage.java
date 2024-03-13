package com.example.PaymentMicroservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class ProduceMessage {

    @Autowired
    KafkaTemplate kafkaTemplate;

    public void sendMessage(String msg) throws InterruptedException {
        kafkaTemplate.send("payed_orders", "Payed: "+ msg);
    }
}

