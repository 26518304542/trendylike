package com.trendylike.orderservice.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trendylike.orderservice.dto.CreateOrderRequest;
import com.trendylike.orderservice.dto.OrderResponse;
import com.trendylike.orderservice.model.Order;
import com.trendylike.orderservice.model.OutboxEvent;
import com.trendylike.orderservice.repository.OrderRepository;
import com.trendylike.orderservice.repository.OutboxRepository;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepo;
    private final OutboxRepository outboxRepo;
    private final ObjectMapper mapper;

    public OrderServiceImpl(OrderRepository orderRepo, OutboxRepository outboxRepo, ObjectMapper mapper) {
        this.orderRepo = orderRepo;
        this.outboxRepo = outboxRepo;
        this.mapper = mapper;
    }

    @Transactional
    @Override
    public OrderResponse createOrder(CreateOrderRequest createOrderRequest) {
        Order order = new Order();
        order.setUserId(createOrderRequest.getUserId());
        order.setTotal(createOrderRequest.getTotal());
        order.setStatus("CREATED");
        order = orderRepo.save(order);

        OrderResponse response = new OrderResponse();
        response.setOrderId(order.getId());
        response.setUserId(order.getUserId());
        response.setTotal(order.getTotal());
        response.setStatus(order.getStatus());

        try {
            OutboxEvent ev = new OutboxEvent();
            ev.setAggregateType("Order");
            ev.setAggregateId(order.getId());
            ev.setType("OrderCreated");
            ev.setPayload(mapper.writeValueAsString(
                    java.util.Map.of("orderId", order.getId(), "total", order.getTotal())));
            outboxRepo.save(ev);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create outbox event", e);
        }

        return response;
    }

    @Override
    public OrderResponse getOrderById(Long id) {
        Order order = orderRepo.findById(id).orElseThrow(()-> new RuntimeException("Order not found"));
        OrderResponse response = new OrderResponse();
        response.setOrderId(order.getId());
        response.setUserId(order.getUserId());
        response.setTotal(order.getTotal());
        response.setStatus(order.getStatus());
        return response;
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepo.findAll();
        List<OrderResponse> responses = orders.stream().map(order -> {
            OrderResponse response = new OrderResponse();
            response.setOrderId(order.getId());
            response.setUserId(order.getUserId());
            response.setTotal(order.getTotal());
            response.setStatus(order.getStatus());
            return response;
        }).toList();
        return responses;
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepo.deleteById(id);
    }

}
