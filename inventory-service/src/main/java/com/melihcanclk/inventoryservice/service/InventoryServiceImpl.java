package com.melihcanclk.inventoryservice.service;

import com.melihcanclk.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Override
    @Transactional
    public boolean checkInventory(String skuCode) {
        return inventoryRepository.findBySkuCode(skuCode).isPresent();
    }
}
