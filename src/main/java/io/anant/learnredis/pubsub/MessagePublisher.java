package io.anant.learnredis.pubsub;

public interface MessagePublisher {
    void publish(Object message);
}
