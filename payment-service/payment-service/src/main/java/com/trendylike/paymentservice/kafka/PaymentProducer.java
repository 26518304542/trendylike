package com.trendylike.paymentservice.kafka;

import java.util.concurrent.CompletableFuture;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentProducer {
    private final KafkaTemplate<String, String> kafka;

    public CompletableFuture<?> publish(String topic, String payload) {
        return kafka.send(topic, payload);
    }
}
