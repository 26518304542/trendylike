package com.trendylike.orderservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trendylike.orderservice.model.OutboxEvent;

public interface OutboxRepository extends JpaRepository<OutboxEvent, Long>{
    List<OutboxEvent> findTop100ByProcessedFalseOrderByCreatedAt();
}
