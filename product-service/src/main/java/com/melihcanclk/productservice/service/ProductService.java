package com.melihcanclk.productservice.service;

import com.melihcanclk.productservice.dto.ProductRequest;
import com.melihcanclk.productservice.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    void createProduct(ProductRequest productRequest);

    List<ProductResponse> getAllProducts();
}
