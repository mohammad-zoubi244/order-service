package com.mohammadzoubi.microservices.order.dto.createorder.request;

import com.mohammadzoubi.microservices.order.config.SystemConfig;
import com.mohammadzoubi.microservices.order.config.SystemMessages;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class CreateOrderRequest {

    private LocalDateTime orderDate;
    @Digits(integer = 10, fraction = 0, message = SystemMessages.CUSTOMER_ID_MAX_LENGTH_MESSAGE)
    private Long customerId;
    @Size(max = SystemConfig.DEFAULT_ITEMS_NUMBER_PER_ORDER, message = SystemMessages.ORDER_EXCEED_ITEMS)
    private List<Item> items;
}
