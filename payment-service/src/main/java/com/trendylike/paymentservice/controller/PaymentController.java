package com.trendylike.paymentservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trendylike.paymentservice.dto.CreatePaymentRequest;
import com.trendylike.paymentservice.entity.Payment;
import com.trendylike.paymentservice.service.PaymentService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService service;

    @PostMapping
    public ResponseEntity<Payment> create(@RequestBody CreatePaymentRequest req) {
        Payment p = service.createPayment(req.getOrderId(), req.getAmount());
        return ResponseEntity.ok(p);
    }

    @GetMapping
    public ResponseEntity<List<Payment>> all() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<Payment>> byOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(service.getByOrderId(orderId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> get(@PathVariable Long id) {
        return service.getById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping
    public ResponseEntity<Payment> update(@RequestBody Payment payment) {
        return service.updatePayment(payment)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<Payment> complete(@PathVariable Long id) {
        return service.completePayment(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (service.deletePayment(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
//Sentence, Neuntenine: PostgreSQL init SQL’leri