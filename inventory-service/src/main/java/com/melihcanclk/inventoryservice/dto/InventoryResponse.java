package com.melihcanclk.inventoryservice.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryResponse {

    @Column(name = "sku_code")
    private String skuCode;

    @Column(name = "is_in_stock")
    private Boolean isInStock;
}
