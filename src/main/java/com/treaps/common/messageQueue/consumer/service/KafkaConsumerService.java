package com.treaps.common.messageQueue.consumer.service;

public interface KafkaConsumerService<T> {
    void startListening( String topic, String groupId, MessageProcessor<T> messageProcessor, Class<T> messageType);
    void startListening( String topic, MessageProcessor<T> messageProcessor, Class<T> messageType);
    void startListening(MessageProcessor<T> messageProcessor,Class<T> messageType);
    void stopListening();
}
