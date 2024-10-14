package com.treaps.common.messageQueue.producer.service;

import com.treaps.common.messageQueue.producer.config.KafkaProducerConfig;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaMessagePublisher implements MessagePublisher {

    @Autowired
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private String topic;
    public KafkaMessagePublisher(@NotNull KafkaTemplate<String, Object> kafkaTemplate,@NotNull @Value("${kafka.topic-name:common}") String topic) {
        this.kafkaTemplate = kafkaTemplate;  // Injected by Spring
        this.topic = topic;                  // Can be passed dynamically or through properties
    }



    @Override
    public void publishMessage(String topic, String key, Object message) {
        kafkaTemplate.send(topic, key, message);
    }

    @Override
    public void publishMessage(String key, Object message) {
        kafkaTemplate.send(topic, key, message);
    }

    @Override
    public void publishMessage(Object message) {
        kafkaTemplate.send(topic, null, message);
    }
}
