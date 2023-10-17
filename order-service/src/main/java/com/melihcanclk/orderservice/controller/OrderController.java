package com.melihcanclk.orderservice.controller;

import com.melihcanclk.orderservice.dto.OrderRequest;
import com.melihcanclk.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "inventory", fallbackMethod = "placeOrderFallback")
    // if response time is more than 3 seconds, TimeoutException will be thrown (these settings given in application.properties)
    @TimeLimiter(name = "inventory")
    // if there is an exception, it will retry 3 times with 5 seconds delay (these settings given in application.properties)
    @Retry(name = "inventory")
    public CompletableFuture<String> placeOrder(@RequestBody OrderRequest orderRequest) {
        log.info("Order service: Placing order");
        return CompletableFuture.supplyAsync(() -> orderService.placeOrder(orderRequest));
    }

    public CompletableFuture<String> placeOrderFallback(OrderRequest orderRequest, RuntimeException e) {
        return  CompletableFuture.supplyAsync(() -> "Order Placed Fallback, please try again later");
    }
}
