package com.mohammadzoubi.microservices.order.dto.getcustomerorder.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CustomerOrdersDetails {

    private Long orderId;
    private String orderNumber;
    private String status;
    private BigDecimal totalAmount;
    private LocalDateTime orderDate;

}
