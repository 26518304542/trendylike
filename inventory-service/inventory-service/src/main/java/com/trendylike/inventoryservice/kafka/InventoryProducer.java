package com.trendylike.inventoryservice.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InventoryProducer {
    //kafka template String string
    //constructor
    //method: publish, parameters:topic, payload , kafka send topic with payload

    private final KafkaTemplate<String, String> kafka;

    public void publish(String topic, String payload){
        kafka.send(topic, payload);
    }
}
