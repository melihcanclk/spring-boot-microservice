package com.melihcanclk.orderservice.service;

import com.melihcanclk.orderservice.dto.OrderRequest;

public interface OrderService {
    String placeOrder(OrderRequest orderRequest);
}
