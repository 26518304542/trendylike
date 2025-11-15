package com.trendylike.orderservice.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trendylike.orderservice.repository.OrderRepository;

@Component
public class OrderEventConsumer {
    private final OrderRepository orderRepo;
    private final ObjectMapper mapper = new ObjectMapper();

    public OrderEventConsumer(OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }

    @KafkaListener(topics = "PaymentCompleted")
    public void handlePaymentCompleted(String payload) {
        try {
            JsonNode n = mapper.readTree(payload);
            Long orderId = n.get("orderId").asLong();
            orderRepo.findById(orderId).ifPresent(o -> {
                o.setStatus("PAID");
                orderRepo.save(o);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @KafkaListener(topics = "PaymentFailed")
    public void handlePaymentFailed(String payload) {
        try {
            JsonNode n = mapper.readTree(payload);
            Long orderId = n.get("orderId").asLong();
            orderRepo.findById(orderId).ifPresent(o -> {
                o.setStatus("FAILED");
                orderRepo.save(o);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
