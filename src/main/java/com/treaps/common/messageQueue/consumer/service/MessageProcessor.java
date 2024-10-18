package com.treaps.common.messageQueue.consumer.service;

public interface MessageProcessor<T> {
    void process(T message);
}
