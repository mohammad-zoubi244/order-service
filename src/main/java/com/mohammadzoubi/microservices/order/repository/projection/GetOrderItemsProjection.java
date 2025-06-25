package com.mohammadzoubi.microservices.order.repository.projection;

import java.math.BigDecimal;

public interface GetOrderItemsProjection {

    String getProductName();

    Integer getQuantity();

    BigDecimal getPrice();

    BigDecimal getTotalPrice();
}
