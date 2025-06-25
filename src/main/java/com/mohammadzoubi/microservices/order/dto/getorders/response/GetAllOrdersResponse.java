package com.mohammadzoubi.microservices.order.dto.getorders.response;

import com.mohammadzoubi.microservices.order.repository.projection.GetOrdersProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class GetAllOrdersResponse {

    private List<OrderDetails> orders;

}
