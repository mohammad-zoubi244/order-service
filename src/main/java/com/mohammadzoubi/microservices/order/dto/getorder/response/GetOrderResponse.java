package com.mohammadzoubi.microservices.order.dto.getorder.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class GetOrderResponse {

    private Long orderId;
    private Long customerId;
    private String orderNumber;
    private String status;
    private BigDecimal totalAmount;
    private LocalDate orderDate;
}
