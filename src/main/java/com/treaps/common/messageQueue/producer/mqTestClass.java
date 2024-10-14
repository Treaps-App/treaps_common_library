package com.treaps.common.messageQueue.producer;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.treaps.common.auth.AuthService;
import com.treaps.common.messageQueue.producer.service.KafkaMessagePublisher;
import com.treaps.common.messageQueue.producer.service.KafkaPublisherFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Component
public class mqTestClass {

    private final KafkaMessagePublisher kafkaMessagePublisher;
    private final AuthService authService;
    private final ObjectMapper objectMapper;

    @Autowired
    public mqTestClass(ObjectMapper objectMapper) {
        this.kafkaMessagePublisher = new KafkaPublisherFactory().createPublisher("123");
        this.authService = new AuthService();
        this.objectMapper = objectMapper;
    }

    @PostMapping("/test")
    public String acceptBookingRequest() {
        return "test";
    }
}
