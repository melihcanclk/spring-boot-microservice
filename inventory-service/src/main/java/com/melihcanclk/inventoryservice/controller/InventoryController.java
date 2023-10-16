package com.melihcanclk.inventoryservice.controller;

import com.melihcanclk.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;
    @GetMapping("/check/{skuCode}")
    @ResponseStatus(HttpStatus.OK)
    public boolean checkInventory(@PathVariable("skuCode") String skuCode) {
        return inventoryService.checkInventory(skuCode);
    }
}
