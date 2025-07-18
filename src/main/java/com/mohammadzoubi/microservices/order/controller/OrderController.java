package com.mohammadzoubi.microservices.order.controller;

import com.mohammadzoubi.microservices.order.config.SystemConfig;
import com.mohammadzoubi.microservices.order.dto.common.PageResult;
import com.mohammadzoubi.microservices.order.dto.createorder.request.CreateOrderRequest;
import com.mohammadzoubi.microservices.order.dto.createorder.response.CreateOrderResponse;
import com.mohammadzoubi.microservices.order.dto.getcustomerorder.response.GetAllCustomerOrdersResponse;
import com.mohammadzoubi.microservices.order.dto.getorder.response.GetOrderResponse;
import com.mohammadzoubi.microservices.order.dto.getorderitems.response.GetOrderItemsResponse;
import com.mohammadzoubi.microservices.order.dto.getorders.response.GetAllOrdersResponse;
import com.mohammadzoubi.microservices.order.dto.getorders.response.GetAllOrdersWithItemsResponse;
import com.mohammadzoubi.microservices.order.dto.updateorder.UpdateOrderRequest;
import com.mohammadzoubi.microservices.order.dto.updateorder.UpdateOrderResponse;
import com.mohammadzoubi.microservices.order.dto.updatestatus.UpdateOrderStatusRequest;
import com.mohammadzoubi.microservices.order.entity.Order;
import com.mohammadzoubi.microservices.order.repository.projection.GetOrderProjection;
import com.mohammadzoubi.microservices.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping()
    public ResponseEntity<CreateOrderResponse> createOrder(
            @RequestBody @Valid final CreateOrderRequest request) {

        final CreateOrderResponse response =
                orderService.createOrder(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @PutMapping("/{orderId}")
    public ResponseEntity<Void> updateOrder(
            @PathVariable(name = "orderId") final Long orderId,
            @RequestBody @Valid UpdateOrderRequest request) {

        orderService.updateOrder(orderId, request);

        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    public ResponseEntity<PageResult<GetAllOrdersResponse>> getOrders(
            @RequestParam(name = "order-number",
                    required = false) final String orderNumber,
            @RequestParam(name = "page-number",
                    defaultValue = SystemConfig.DEFAULT_PAGE_NUMBER,
                    required = false) final Integer pageNumber,
            @RequestParam(name = "page-size",
                    defaultValue = SystemConfig.DEFAULT_PAGE_SIZE,
                    required = false) final Integer pageSize) {

        final PageResult<GetAllOrdersResponse> orders =
                orderService.getOrders(orderNumber, pageNumber, pageSize);

        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<GetOrderResponse> getOrderByOrderId(
            @PathVariable(name = "orderId") final Long orderId) {

        final GetOrderResponse orders = orderService.getOrderByOrderId(orderId);

        return ResponseEntity.status(HttpStatus.OK).body(orders);

    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<PageResult<GetAllCustomerOrdersResponse>> getCustomerOrdersByCustomerId(
            @PathVariable(name = "customerId") final Long customerId,
            @RequestParam(name = "page-number",
                    defaultValue = SystemConfig.DEFAULT_PAGE_NUMBER,
                    required = false) final Integer pageNumber,
            @RequestParam(name = "page-size",
                    defaultValue = SystemConfig.DEFAULT_PAGE_SIZE,
                    required = false) final Integer pageSize) {

        final PageResult<GetAllCustomerOrdersResponse> orders =
                orderService.getOrdersByCustomerId(customerId, pageNumber, pageSize);

        return ResponseEntity.status(HttpStatus.OK).body(orders);

    }

    @GetMapping("/{orderId}/items")
    public ResponseEntity<GetOrderItemsResponse> getOrderItems(
            @PathVariable(name = "orderId") final Long orderId,
            @RequestParam(name = "page-number",
                    defaultValue = SystemConfig.DEFAULT_PAGE_NUMBER,
                    required = false) final Integer pageNumber,
            @RequestParam(name = "page-size",
                    defaultValue = SystemConfig.DEFAULT_PAGE_SIZE,
                    required = false) final Integer pageSize) {

        final GetOrderItemsResponse items = orderService.getOrderItems(orderId, pageNumber, pageSize);

        return ResponseEntity.status(HttpStatus.OK).body(items);

    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<Void> updateOrderStatus(
            @PathVariable(name = "orderId") final Long orderId,
            @RequestBody @Valid UpdateOrderStatusRequest request) {

        orderService.updateOrderStatus(orderId, request);

        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(
            @PathVariable(name = "orderId") final Long orderId) {

        orderService.deleteOrder(orderId);

        return ResponseEntity.noContent().build();
    }
}
