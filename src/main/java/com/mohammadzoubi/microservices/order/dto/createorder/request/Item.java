package com.mohammadzoubi.microservices.order.dto.createorder.request;

import com.mohammadzoubi.microservices.order.config.SystemConfig;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Item {

    @NotBlank
    private String productName;
    @Min(SystemConfig.DEFAULT_QUANTITY)
    private int quantity;
    @Schema(defaultValue = "0.0")
    private double price;
}
