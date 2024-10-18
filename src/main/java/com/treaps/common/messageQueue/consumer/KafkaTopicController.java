package com.treaps.common.messageQueue.consumer;
import com.treaps.common.messageQueue.consumer.service.DynamicKafkaConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaTopicController {

    @Autowired
    private DynamicKafkaConsumerService kafkaConsumerService;

    @GetMapping("/startKafkaConsumer")
    public String startKafkaConsumer() {
        // Start listening to the configured topic and group ID
        kafkaConsumerService.startListening();
        return "Kafka Consumer started.";
    }

    @GetMapping("/stopKafkaConsumer")
    public String stopKafkaConsumer() {
        kafkaConsumerService.stopListening();
        return "Kafka Consumer stopped.";
    }
}
