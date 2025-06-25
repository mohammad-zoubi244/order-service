package com.mohammadzoubi.microservices.order.repository.projection;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface GetOrdersProjection {

    Long getOrderId();
    Long getCustomerId();
    String getOrderNumber();
    String getStatus();
    BigDecimal getTotalAmount();
    LocalDateTime getOrderDate();
}
