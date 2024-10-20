package com.treaps.common.messageQueue.consumer;
import com.treaps.common.messageQueue.consumer.service.KafkaConsumerService;
import com.treaps.common.messageQueue.consumer.service.MessageProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KafkaTopic1Controller {

    @Autowired
    private KafkaConsumerService<String> kafkaConsumerService;

    public <T> String startKafkaConsumer() {
        // Start listening to the configured topic and group ID
        kafkaConsumerService.startListening(
            "topic2",
            new MessageProcessor<String>() {

                @Override
                public void process(String key, String message) {
                    System.out.println(key);
                }
                
            },
            String.class
        );
        return "Kafka Consumer started.";
    }

    public String stopKafkaConsumer() {
        kafkaConsumerService.stopListening();
        return "Kafka Consumer stopped.";
    }
}
