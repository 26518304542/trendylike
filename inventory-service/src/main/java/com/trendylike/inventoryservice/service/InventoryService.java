package com.trendylike.inventoryservice.service;

import org.springframework.stereotype.Service;

import com.trendylike.inventoryservice.model.Inventory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService {public Object getAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

public Object getByProductId(Long productId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getByProductId'");
}

public Inventory createInventory(Inventory inventory) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'createInventory'");
}

public Object updateInventory(Long id, Inventory inventory) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateInventory'");
}

public Object adjustAvailable(Long id, Integer adjustment) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'adjustAvailable'");
}

public boolean deleteInventory(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteInventory'");
}



}
