package com.melihcanclk.productservice.service;

import com.melihcanclk.productservice.dto.ProductRequest;
import com.melihcanclk.productservice.dto.ProductResponse;
import com.melihcanclk.productservice.model.Product;
import com.melihcanclk.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

//RequiredArgsConstructor creates a constructor with required arguments.
@RequiredArgsConstructor
@Slf4j
@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    @Override
    public void createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
        productRepository.save(product);
        log.info("Product created: {}", product);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();

        return products.stream().map((product -> ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build())).toList();

    }
}
