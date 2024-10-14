package com.treaps.common.messageQueue.producer.service;

public interface MessagePublisher {
    void publishMessage(String topic, String key, Object message);
    void publishMessage(String key, Object message);
    void publishMessage(Object message);
}
