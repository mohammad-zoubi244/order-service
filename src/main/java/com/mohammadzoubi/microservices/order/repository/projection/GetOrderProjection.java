package com.mohammadzoubi.microservices.order.repository.projection;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface GetOrderProjection {

    Long getOrderId();
    Long getCustomerId();
    String getOrderNumber();
    String getStatus();
    BigDecimal getTotalAmount();
    LocalDateTime getOrderDate();
}
