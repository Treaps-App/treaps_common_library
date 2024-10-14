package com.treaps.common.messageQueue.producer.service;

public interface PubliserFactory {
    MessagePublisher createPublisher( String topic);
}
