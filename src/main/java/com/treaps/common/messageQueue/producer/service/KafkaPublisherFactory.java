package com.treaps.common.messageQueue.producer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaPublisherFactory implements PubliserFactory{

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public KafkaMessagePublisher createPublisher(String topic) {
        return new KafkaMessagePublisher(kafkaTemplate, topic);
    }
}
