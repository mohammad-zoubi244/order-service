package com.mohammadzoubi.microservices.order.dto.getorders.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class OrderDetails {

    private Long orderId;
    private Long customerId;
    private String orderNumber;
    private String status;
    private BigDecimal totalAmount;
    private LocalDateTime orderDate;

}
