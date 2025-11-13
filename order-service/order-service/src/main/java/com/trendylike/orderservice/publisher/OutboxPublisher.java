package com.trendylike.orderservice.publisher;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trendylike.orderservice.model.OutboxEvent;
import com.trendylike.orderservice.repository.OutboxRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OutboxPublisher {
    private final OutboxRepository outboxRepo;
    private final KafkaTemplate<String, String> kafka;
    private final ObjectMapper mapper;

    @Scheduled(fixedDelayString = "2000") // local için, prod'da Debezium/CDC önerilir
    public void pollAndPublish() {
        List<OutboxEvent> events = outboxRepo.findTop100ByProcessedFalseOrderByCreatedAt();
        for (OutboxEvent e : events) {
            try {
                kafka.send(e.getType(), e.getPayload()).get(5, TimeUnit.SECONDS);
                e.setProcessed(true);
                outboxRepo.save(e);
            } catch (Exception ex) {
                // retry logic or move to dead-letter
            }
        }
    }
}

