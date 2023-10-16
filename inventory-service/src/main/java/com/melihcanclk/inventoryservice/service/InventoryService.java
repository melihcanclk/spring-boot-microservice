package com.melihcanclk.inventoryservice.service;


import com.melihcanclk.inventoryservice.dto.InventoryResponse;

import java.util.List;

public interface InventoryService {

    boolean checkInventory(String skuCode);

    List<InventoryResponse> checkInventory(List<String> skuCodeList);
}
