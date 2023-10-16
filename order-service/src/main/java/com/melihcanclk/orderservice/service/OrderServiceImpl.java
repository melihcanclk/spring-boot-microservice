package com.melihcanclk.orderservice.service;

import com.melihcanclk.orderservice.dto.OrderLineItemDTO;
import com.melihcanclk.orderservice.dto.OrderRequest;
import com.melihcanclk.orderservice.model.Order;
import com.melihcanclk.orderservice.model.OrderLineItem;
import com.melihcanclk.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;

    @Override
    public String placeOrder(OrderRequest orderRequest) {
        Order order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .build();

        List<OrderLineItem> listOfOrderLineItem = orderRequest.getOrderLineItemsDTO().stream().map(this::mapToDTO).toList();
        order.setOrderLineItems(listOfOrderLineItem);

        orderRepository.save(order);
        return order.getOrderNumber();
    }

    private OrderLineItem mapToDTO(OrderLineItemDTO orderLineItemDTO) {
        return OrderLineItem.builder()
                .price(orderLineItemDTO.getPrice())
                .skuCode(orderLineItemDTO.getSkuCode())
                .quantity(orderLineItemDTO.getQuantity())
                .build();

    }


}
