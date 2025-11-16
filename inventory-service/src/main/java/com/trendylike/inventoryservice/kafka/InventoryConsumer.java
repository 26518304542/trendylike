package com.trendylike.inventoryservice.kafka;

import java.util.Map;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trendylike.inventoryservice.dto.InventoryFailedEvent;
import com.trendylike.inventoryservice.dto.InventoryReservedEvent;
import com.trendylike.inventoryservice.dto.OrderCreatedEvent;
import com.trendylike.inventoryservice.model.Inventory;
import com.trendylike.inventoryservice.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class InventoryConsumer {

    private final InventoryRepository repo;
    private final KafkaTemplate<String, String> kafka;
    private final ObjectMapper mapper = new ObjectMapper();

    @KafkaListener(topics = "order-created", groupId = "inventory-group")
    public void consume(String payload) {
        try {
            OrderCreatedEvent event = mapper.readValue(payload, OrderCreatedEvent.class);
            log.info("Received OrderCreatedEvent: {}", event);

            Inventory inventory = repo.findById(event.getProductId()).orElse(null);

            if(inventory != null && inventory.getAvailable() >= event.getQuantity()){
                inventory.setAvailable(inventory.getAvailable() - event.getQuantity());
                repo.save(inventory);

                InventoryReservedEvent reserved = new InventoryReservedEvent(
                    event.getOrderId(),
                    event.getProductId(),
                    event.getQuantity()
                );

                kafka.send("inventory-reserved", mapper.writeValueAsString(reserved));
                log.info("Inventory reserved and event sent: {}", reserved);
            }else {
                    InventoryFailedEvent failed = new InventoryFailedEvent(
                    event.getOrderId(),
                    "Not enough stock"
                );

                kafka.send("inventory-failed", mapper.writeValueAsString(failed));
                log.info("Sent inventory-failed event for order {}", event.getOrderId());
            }

        } catch (Exception e) {
            log.error("Error processing OrderCreatedEvent: ", e);
        }
    }
}
//inventory-service: InventoryProducer.java (opsiyonel helper)