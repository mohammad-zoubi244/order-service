package com.mohammadzoubi.microservices.order.dto.getorders.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class OrdersWithItems {
    private Long orderId;
    private Long customerId;
    private String orderNumber;
    private String status;
    private BigDecimal totalAmount;
    private LocalDateTime orderDate;
    private List<OrderItems> items;

    public List<OrderItems> getOrderItems() {
        if (items == null) {
            items = new ArrayList<>();
        }
        return items;
    }
}
