package com.mohammadzoubi.microservices.order.dto.getorders.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class GetAllOrdersWithItemsResponse {

    private List<OrdersWithItems> orders;
}
