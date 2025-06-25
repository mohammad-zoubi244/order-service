package com.mohammadzoubi.microservices.order.dto.updateorder;

import com.mohammadzoubi.microservices.order.config.SystemMessages;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateOrderRequest {

    @Digits(integer = 10, fraction = 0, message = SystemMessages.CUSTOMER_ID_MAX_LENGTH_MESSAGE)
    private Long customerId;
}
