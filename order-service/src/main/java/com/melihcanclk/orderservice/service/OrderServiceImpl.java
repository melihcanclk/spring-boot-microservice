package com.melihcanclk.orderservice.service;

import com.melihcanclk.orderservice.dto.InventoryResponse;
import com.melihcanclk.orderservice.dto.OrderLineItemDTO;
import com.melihcanclk.orderservice.dto.OrderRequest;
import com.melihcanclk.orderservice.model.Order;
import com.melihcanclk.orderservice.model.OrderLineItem;
import com.melihcanclk.orderservice.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;

    private final WebClient webClient;

    @Override
    public String placeOrder(OrderRequest orderRequest) {
        Order order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .build();

        List<OrderLineItem> listOfOrderLineItem = orderRequest.getOrderLineItemsDTO().stream().map(this::mapToDTO).toList();
        order.setOrderLineItems(listOfOrderLineItem);

        List<String> listOfSkuCodes = order.getOrderLineItems().stream().map(OrderLineItem::getSkuCode).toList();


        // call inventory service to check if inventory is in stock
        InventoryResponse[] inventoryResponseArray = webClient.get()
                .uri("http://localhost:8082/api/v1/inventory", uriBuilder -> uriBuilder.queryParam("skuCode", listOfSkuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        if (inventoryResponseArray != null) {
            boolean isAllProductsInStock = Arrays.stream(inventoryResponseArray).allMatch(InventoryResponse::getIsInStock);
            if(isAllProductsInStock){
                orderRepository.save(order);
                log.info("Order service: Order is placed successfully");
                return order.getOrderNumber();
            } else {
                log.error("Order service: Product is not in stock");
                throw new IllegalArgumentException("Order service: Product is not in stock, please try again later");
            }
        } else {
            log.error("Order service: Inventory response is null");
            throw new IllegalArgumentException("Order service: Inventory response is null");
        }
    }

    private OrderLineItem mapToDTO(OrderLineItemDTO orderLineItemDTO) {
        return OrderLineItem.builder()
                .price(orderLineItemDTO.getPrice())
                .skuCode(orderLineItemDTO.getSkuCode())
                .quantity(orderLineItemDTO.getQuantity())
                .build();

    }


}
