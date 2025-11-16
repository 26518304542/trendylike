package com.trendylike.inventoryservice.dto;

import lombok.Data;

@Data
public class OrderCreatedEvent {
    private Long orderId;
    private Long productId;
    private Integer quantity;
}
