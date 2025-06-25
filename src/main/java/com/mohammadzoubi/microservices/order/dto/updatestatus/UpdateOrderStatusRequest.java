package com.mohammadzoubi.microservices.order.dto.updatestatus;

import com.mohammadzoubi.microservices.order.enums.OrderStatusEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateOrderStatusRequest {

    private OrderStatusEnum status;
}
