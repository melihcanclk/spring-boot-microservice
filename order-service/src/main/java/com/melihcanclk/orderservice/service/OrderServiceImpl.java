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
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final WebClient.Builder webClientBuilder;

    @Override
    public String placeOrder(OrderRequest orderRequest) {
        Order order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .build();

        List<OrderLineItem> listOfOrderLineItem = orderRequest.getOrderLineItemsDTO().stream().map(this::mapToDTO).toList();
        order.setOrderLineItems(listOfOrderLineItem);

        List<String> listOfSkuCodes = order.getOrderLineItems().stream().map(OrderLineItem::getSkuCode).toList();

        // call inventory service to check if inventory is in stock
        InventoryResponse[] inventoryResponseArray = webClientBuilder.build().get()
                .uri("http://inventory-service/api/v1/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", listOfSkuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        boolean allProductsInStock = false;
        if (inventoryResponseArray != null) {
            allProductsInStock = Arrays.stream(inventoryResponseArray)
                    .allMatch(InventoryResponse::getIsInStock);

            if (allProductsInStock) {
                orderRepository.save(order);
                // publish Order Placed Event
                return "Order Placed";
            } else {
                throw new IllegalArgumentException("Product is not in stock, please try again later");
            }
        }  else {
            throw new IllegalArgumentException("Product is not in stock, please try again later");
        }
    }

    private OrderLineItem mapToDTO(OrderLineItemDTO orderLineItemDTO) {
        return OrderLineItem.builder()
                .skuCode(orderLineItemDTO.getSkuCode())
                .quantity(orderLineItemDTO.getQuantity())
                .build();

    }


}
