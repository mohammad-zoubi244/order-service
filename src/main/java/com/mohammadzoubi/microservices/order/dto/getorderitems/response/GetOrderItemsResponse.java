package com.mohammadzoubi.microservices.order.dto.getorderitems.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
public class GetOrderItemsResponse {

    private List<OrderItemsDetails> items;
}
