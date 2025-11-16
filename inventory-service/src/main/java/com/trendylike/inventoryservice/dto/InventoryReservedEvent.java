package com.trendylike.inventoryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
@AllArgsConstructor
public class InventoryReservedEvent {
    private Long orderId;
    private Long productId;
    private Integer quantity;
}
