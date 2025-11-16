package com.trendylike.paymentservice.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderReservedEvent {
    private Long orderId;
    private Long productId;
    private Integer quantity;
    private BigDecimal amount;
}
