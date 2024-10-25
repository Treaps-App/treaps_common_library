package com.treaps.common.messageQueue.consumer.service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.treaps.common.errorHandling.exception.commonCustomException.MessageProcessorNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")  // This ensures that a new instance is created for each request
public class DynamicKafkaConsumerService<T> implements KafkaConsumerService<T> {

    @Value("${dynamic.kafka.topic:default-topic-test}")
    private String defaultKafkaTopic;

    @Value("${dynamic.kafka.group-id:default-group-test}")
    private String defaultKafkaGroup;

    @Autowired
    private ConsumerFactory<String, T> consumerFactory;

    private MessageProcessor<T> messageProcessor;
    private Class<T> messageType; 

    @Autowired
    private ObjectMapper objectMapper;

    // A reference to the container to manage start/stop
    private ConcurrentMessageListenerContainer<String, T> container;

    private static final Logger logger = LoggerFactory.getLogger(DynamicKafkaConsumerService.class);


    /**
     * Starts a Kafka listener for the given topic(s) with the given groupID.
     * The messageProcessor is used to process the messages.
     * If messageProcessor is null, a MessageProcessorNotFoundException is thrown.
     * If the container is currently running, it is stopped before starting the new one.
     * If an error occurs during the creation of the container, a MessageProcessorNotFoundException is thrown.
     * @param topic The topic(s) to listen to. Can be a single topic or a comma separated list of topics.
     * @param groupId The groupID to use for the consumer.
     */
    private void startListening( String topic, String groupId) {
        if (messageProcessor == null || messageType == null) {
            throw new MessageProcessorNotFoundException("No MessageProcessor provided for topic: " + topic);
        }
        stopListening();
        logger.info("Starting Kafka listener for topics: {} with groupId: {}", String.join(", ", topic), groupId);
        
        ContainerProperties containerProps = new ContainerProperties(topic);
        containerProps.setGroupId(groupId);
        containerProps.setMessageListener((MessageListener<String, T>) record -> {
            try {
                T value = objectMapper.readValue(record.value().toString(), messageType);
                messageProcessor.process(record.key(),value);
            } catch (JsonMappingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (JsonProcessingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        
        container = new ConcurrentMessageListenerContainer<>(consumerFactory, containerProps);
        container.setBeanName("dynamicKafkaListener");

        
        container.start();
// TODO: proper error handeling 
        // throw new MessageProcessorNotFoundException("No MessageProcessor found for message type: " + record.value().getClass().getSimpleName());
    }

    @Override
    public void startListening( String topic, String groupId, MessageProcessor<T> messageProcessor, Class<T> messageType){
        this.messageType = messageType;
        this.messageProcessor = messageProcessor;
        startListening(topic, groupId);

    }
    @Override
    public void startListening( String topic, MessageProcessor<T> messageProcessor, Class<T> messageType){
        this.messageType = messageType;
        this.messageProcessor = messageProcessor;
        startListening(topic, defaultKafkaGroup);

        
    }
    @Override
    public void startListening(MessageProcessor<T> messageProcessor,Class<T> messageType){
        this.messageType = messageType;
        this.messageProcessor = messageProcessor;
        startListening(defaultKafkaTopic, defaultKafkaGroup);
    }

    @Override
    public void stopListening() {
        // Stop and clear the container if it is running
        if (container != null) {

            logger.info("Stopping Kafka listener");
            container.stop();
            container = null;
        }
    }
}
