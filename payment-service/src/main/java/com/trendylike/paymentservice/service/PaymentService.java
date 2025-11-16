package com.trendylike.paymentservice.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.trendylike.paymentservice.entity.Payment;
import com.trendylike.paymentservice.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository repo;

        public Payment createPayment(Long orderId, BigDecimal amount) {
        Payment p = new Payment(orderId, amount, "PENDING");
        return repo.save(p);
    }

    public Optional<Payment> getById(Long id) {
        return repo.findById(id);
    }

    public List<Payment> getByOrderId(Long orderId) {
        return repo.findByOrderId(orderId);
    }

    public List<Payment> getAll() {
        return repo.findAll();
    }

    public Optional<Payment> updatePayment(Payment updated){
        return repo.findById(updated.getId()).map(p -> {
            p.setAmount(updated.getAmount());
            p.setStatus(updated.getStatus());
            return repo.save(p);
        });
    }

    public Optional<Payment> completePayment(Long id){
        return repo.findById(id).map(p-> {
            p.setStatus("COMPLETED");
            return repo.save(p);
        });
    }

    public boolean deletePayment(Long id){
        return repo.findById(id).map(p -> {
            repo.delete(p);
            return true;
        }).orElse(false);
    }
}
