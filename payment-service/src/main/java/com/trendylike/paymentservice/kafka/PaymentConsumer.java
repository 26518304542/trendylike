package com.trendylike.paymentservice.kafka;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trendylike.paymentservice.entity.Payment;
import com.trendylike.paymentservice.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentConsumer {

    private final PaymentRepository paymentRepository;
    private final KafkaTemplate<String, String> kafka;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "InventoryReserved")
    public void handleInventoryReserved(String payload){
        try{
            JsonNode node = objectMapper.readTree(payload);
            Long orderId = node.get("orderId").asLong();
            BigDecimal amount = node.has("amount") ? new BigDecimal(node.get("amount").asText()) : BigDecimal.valueOf(100);

            boolean success = simulatePayment(orderId, amount);

            Payment payment = new Payment(orderId, amount, success ? "COMPLETED": "FAILED");
            paymentRepository.save(payment);

            String event = objectMapper.writeValueAsString(Map.of("orderId", orderId, "paymentId", payment.getId()));
            kafka.send(success ? "PaymentCompleted" : "PaymentFailed", event);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean simulatePayment(Long orderId, BigDecimal amount) {
        // deterministic simulation for demo. Could randomize or use rules.
        return true;
    }
}
