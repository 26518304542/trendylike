package com.trendylike.inventoryservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trendylike.inventoryservice.model.Inventory;
import com.trendylike.inventoryservice.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository repository;

    public List<Inventory> getAll() {
        return repository.findAll();
    }

    public Optional<Inventory> getByProductId(Long productId) {
        return repository.findByProductId(productId);
    }

    public Inventory createInventory(Inventory inv) {
        return repository.save(inv);
    }

    public Optional<Inventory> updateInventory(Long id, Inventory updated) {
        return repository.findById(id).map(inv -> {
            inv.setProductId(updated.getProductId());
            inv.setAvailable(updated.getAvailable());
            return repository.save(inv);
        });
    }

    @Transactional
    public Optional<Inventory> adjustAvailable(Long id, Integer adjustment) {
        return repository.findById(id).map(inv -> {
            inv.setAvailable(inv.getAvailable() + adjustment);
            return repository.save(inv);
        });
    }

    public boolean deleteInventory(Long id) {
        return repository.findById(id).map(inv -> {
            repository.delete(inv);
            return true;
        }).orElse(false);
    }
}
