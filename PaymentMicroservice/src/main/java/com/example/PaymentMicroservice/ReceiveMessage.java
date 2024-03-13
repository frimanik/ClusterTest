package com.example.PaymentMicroservice;

import jakarta.annotation.PostConstruct;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

@Service
public class ReceiveMessage {

    private final static String TOPIC = "new_orders";
    private final static String BOOTSTRAP_SERVERS =
            "192.168.99.100:9092";

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

    private KafkaConsumer<Long, String> createConsumer() {
        final Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG,
                "OrderConsumer");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                LongDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class.getName());

        final KafkaConsumer<Long, String> consumer =
                new KafkaConsumer<>(props);

        consumer.subscribe(Collections.singletonList(TOPIC));
        return consumer;
    }

    public void runConsumer() throws InterruptedException {
        final KafkaConsumer<Long, String> kafkaConsumer = createConsumer();

        while (true) {
            final ConsumerRecords<Long, String> consumerRecords =
                    kafkaConsumer.poll(Duration.ofSeconds(1));

            consumerRecords.forEach(record -> {
                System.out.printf("Consumer Record:(%d, %s, %d, %d)\n",
                        record.key(), record.value(),
                        record.partition(), record.offset());
                try {
                    produceMessage.sendMessage(record.value());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });

            kafkaConsumer.commitAsync();
        }
    }
}
