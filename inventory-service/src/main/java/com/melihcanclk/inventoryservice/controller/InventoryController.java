package com.melihcanclk.inventoryservice.controller;

import com.melihcanclk.inventoryservice.dto.InventoryResponse;
import com.melihcanclk.inventoryservice.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @Operation(summary = "Inventory Check", description = "Checks the inventory of the given sku code",
            security = @SecurityRequirement(name = "security_auth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @GetMapping("/{skuCode}")
    @ResponseStatus(HttpStatus.OK)
    public boolean checkInventory(@PathVariable("skuCode") String skuCode) {
        return inventoryService.checkInventory(skuCode);
    }

    @Operation(summary = "Inventory Check", description = "Checks the inventory of the given sku codes",
            security = @SecurityRequirement(name = "security_auth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> checkInventory(@RequestParam("skuCode") List<String> skuCodeList) {
        return inventoryService.checkInventory(skuCodeList);
    }
}
