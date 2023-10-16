package com.melihcanclk.inventoryservice.repository;

import com.melihcanclk.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findBySkuCode(String skuCode);

    Collection<Inventory> findAllBySkuCodeIn(List<String> skuCodeList);
}
