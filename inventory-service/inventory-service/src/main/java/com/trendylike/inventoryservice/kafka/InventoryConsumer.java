package com.trendylike.inventoryservice.kafka;

import java.util.Map;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trendylike.inventoryservice.model.Inventory;
import com.trendylike.inventoryservice.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InventoryConsumer {

    private final InventoryRepository repo;
    private final KafkaTemplate<String, String> kafka;
    private final ObjectMapper mapper = new ObjectMapper();

    @KafkaListener(topics = "order-created", groupId = "inventory-service")
    public void handleOrderCreated(String payload) {
        try {
            JsonNode node = mapper.readTree(payload);
            Long orderId = node.get("orderId").asLong();
            Long productId = node.has("productId") ? node.get("productId").asLong() : 1L;
            int qty = node.has("qty") ? node.get("qty").asInt() : 1;

            Inventory inventory = repo.findByProductId(productId).orElse(null);
            if (inventory.getAvailable() >= qty) {
                inventory.setAvailable(inventory.getAvailable() - qty);
                repo.save(inventory);

                String event = mapper.writeValueAsString(Map.of("orderıd", orderId, "productId", productId));
                kafka.send("InventoryReserved", event);
            } else {
                String event = mapper.writeValueAsString(Map.of("orderId", orderId));
                kafka.send("InventoryFailed", event);
            }
        } catch (Exception e) {

        }
    }
}
//inventory-service: InventoryProducer.java (opsiyonel helper)