package com.trendylike.orderservice.service;

import java.util.List;

import com.trendylike.orderservice.dto.CreateOrderRequest;
import com.trendylike.orderservice.dto.OrderResponse;
import com.trendylike.orderservice.model.Order;

public interface OrderService {
    public OrderResponse createOrder(CreateOrderRequest createOrderRequest);

    public OrderResponse getOrderById(Long id);

    public List<OrderResponse> getAllOrders();

    public void deleteOrder(Long id);
}
