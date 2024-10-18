package com.treaps.common.messageQueue.consumer.service;
import com.treaps.common.errorHandling.exception.MessageProcessorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DynamicKafkaConsumerService<T> {

    @Value("${dynamic.kafka.topic:default-topic-test}")
    private String dynamicKafkaTopic;

    @Value("${dynamic.kafka.group-id:default-group-test}")
    private String dynamicKafkaGroupId;

    @Autowired
    private ConsumerFactory<String, T> consumerFactory;

    // Inject the processor as an optional dependency
    @Autowired
    private Optional<MessageProcessor<T>> messageProcessor;

    // A reference to the container to manage start/stop
    private ConcurrentMessageListenerContainer<String, T> container;

    public void startListening() {
        // Stop any existing listener if active
        stopListening();

        // Set the dynamic topic and group ID from the injected values
        ContainerProperties containerProps = new ContainerProperties(dynamicKafkaTopic);
        containerProps.setGroupId(dynamicKafkaGroupId);
        containerProps.setMessageListener((MessageListener<String, T>) record -> {
            System.out.println("Received message from Kafka: " + record.value());
            messageProcessor.ifPresentOrElse(
                    processor -> processor.process(record.value()),
                    () -> {
                        throw new MessageProcessorNotFoundException("No MessageProcessor found for message type: " + record.value().getClass().getSimpleName());
                    }
            );
        });

        // Initialize the container
        container = new ConcurrentMessageListenerContainer<>(consumerFactory, containerProps);
        container.setBeanName("dynamicKafkaListener");

        // Start the listener container
        container.start();
    }

    public void stopListening() {
        // Stop and clear the container if it is running
        if (container != null) {
            container.stop();
            container = null;
        }
    }
}
