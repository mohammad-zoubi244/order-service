package com.mohammadzoubi.microservices.order.dto.createorder.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateOrderResponse {

    private Long orderid;
    private String orderNumber;

}
