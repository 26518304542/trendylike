package com.trendylike.paymentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentFailedEvent {
    private Long orderId;
    private String reason;
}
