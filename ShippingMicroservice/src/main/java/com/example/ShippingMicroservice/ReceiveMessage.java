package com.example.ShippingMicroservice;

import jakarta.annotation.PostConstruct;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ReceiveMessage {

    private final static String TOPIC = "payed_orders";
    private final static String BOOTSTRAP_SERVERS =
            "192.168.99.100:9092,192.168.99.100:9093,192.168.99.100:9094";

    @Autowired
    ProduceMessage produceMessage;
    public ReceiveMessage(ProduceMessage produceMessage) {
        this.produceMessage = produceMessage;
    }

    @PostConstruct
    public void init() {
        try {
            runConsumer();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void runConsumer() throws InterruptedException {

        AtomicInteger counter = new AtomicInteger(0);

        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "shipmentMicroservice-streams-app");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> source = builder.stream("payed_orders");
        KStream<String, String> modifiedStream = source.mapValues(value -> value + "ordinal_value "+ counter.getAndIncrement());
        modifiedStream.to("sent_orders");

        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();
    }
}
