package com.performance.producer;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
public class KafkaProducer {

    @Channel("payments-out")
    Emitter<String> emitter;

    public void send(String message) {
        emitter.send(message);
    }

}
