package com.mohammadzoubi.microservices.order.dto.getcustomerorder.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class GetAllCustomerOrdersResponse {
    private List<CustomerOrdersDetails> orders;

}
