package com.melihcanclk.inventoryservice.controller;

import com.melihcanclk.inventoryservice.dto.InventoryResponse;
import com.melihcanclk.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/{skuCode}")
    @ResponseStatus(HttpStatus.OK)
    public boolean checkInventory(@PathVariable("skuCode") String skuCode) {
        return inventoryService.checkInventory(skuCode);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> checkInventory(@RequestParam("skuCode") List<String> skuCodeList) {
        return inventoryService.checkInventory(skuCodeList);
    }
}
