package com.trendylike.paymentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PaymentCompletedEvent {
    private Long orderId;
    private Long paymentId;
}
