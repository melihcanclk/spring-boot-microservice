package com.melihcanclk.inventoryservice.service;

import com.melihcanclk.inventoryservice.dto.InventoryResponse;
import com.melihcanclk.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Override
    public boolean checkInventory(String skuCode) {
        return inventoryRepository.findBySkuCode(skuCode).isPresent();
    }

    @Override
    public List<InventoryResponse> checkInventory(List<String> skuCodeList) {
        log.info("Inventory service: Checking inventory for sku codes: {}", skuCodeList);
        return inventoryRepository.findAllBySkuCodeIn(skuCodeList).stream()
                .map(inventory ->
                    InventoryResponse.builder()
                            .skuCode(inventory.getSkuCode())
                            .isInStock(inventory.getQuantity() > 0)
                            .build()
                ).toList();
    }
}
