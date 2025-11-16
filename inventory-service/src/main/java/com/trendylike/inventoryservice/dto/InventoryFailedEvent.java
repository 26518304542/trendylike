package com.trendylike.inventoryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class InventoryFailedEvent {
    private Long orderId;
    private String reason;

}
