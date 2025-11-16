package com.trendylike.paymentservice.kafka;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trendylike.paymentservice.dto.OrderReservedEvent;
import com.trendylike.paymentservice.dto.PaymentCompletedEvent;
import com.trendylike.paymentservice.entity.Payment;
import com.trendylike.paymentservice.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentConsumer {

    private final PaymentRepository paymentRepository;
    private final KafkaTemplate<String, String> kafka;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "inventory-reserved", groupId = "payment-group")
    public void handleInventoryReserved(String payload){
        try{
            OrderReservedEvent event = objectMapper.readValue(payload, OrderReservedEvent.class);
            log.info("Received inventory-reserved event: {}", payload);

            BigDecimal amount = event.getAmount() != null ?
                    event.getAmount() :
                    BigDecimal.valueOf(100);

            Payment payment = new Payment(event.getOrderId(), amount, "COMPLETED");
            paymentRepository.save(payment);

            PaymentCompletedEvent completed =
                    new PaymentCompletedEvent(event.getOrderId(), payment.getId());

            kafka.send("payment-completed", objectMapper.writeValueAsString(completed));

            log.info("Payment completed for order {}", event.getOrderId());

        }catch (Exception e) {
            log.error("PaymentConsumer error", e);
        }
    }

    private boolean simulatePayment(Long orderId, BigDecimal amount) {
        return true;
    }
}
