package com.mohammadzoubi.microservices.order.service;

import com.mohammadzoubi.microservices.order.config.ErrorMessages;
import com.mohammadzoubi.microservices.order.config.SystemConfig;
import com.mohammadzoubi.microservices.order.dto.common.PageResult;
import com.mohammadzoubi.microservices.order.dto.createorder.request.CreateOrderRequest;
import com.mohammadzoubi.microservices.order.dto.createorder.request.Item;
import com.mohammadzoubi.microservices.order.dto.createorder.response.CreateOrderResponse;
import com.mohammadzoubi.microservices.order.dto.getcustomerorder.response.CustomerOrdersDetails;
import com.mohammadzoubi.microservices.order.dto.getcustomerorder.response.GetAllCustomerOrdersResponse;
import com.mohammadzoubi.microservices.order.dto.getorder.response.GetOrderResponse;
import com.mohammadzoubi.microservices.order.dto.getorderitems.response.GetOrderItemsResponse;
import com.mohammadzoubi.microservices.order.dto.getorderitems.response.OrderItemsDetails;
import com.mohammadzoubi.microservices.order.dto.getorders.response.*;
import com.mohammadzoubi.microservices.order.dto.updateorder.UpdateOrderRequest;
import com.mohammadzoubi.microservices.order.dto.updateorder.UpdateOrderResponse;
import com.mohammadzoubi.microservices.order.dto.updatestatus.UpdateOrderStatusRequest;
import com.mohammadzoubi.microservices.order.entity.Order;
import com.mohammadzoubi.microservices.order.entity.OrderItem;
import com.mohammadzoubi.microservices.order.enums.OrderStatusEnum;
import com.mohammadzoubi.microservices.order.exception.ResourceNotFoundException;
import com.mohammadzoubi.microservices.order.repository.OrderRepository;
import com.mohammadzoubi.microservices.order.repository.projection.GetOrderItemsProjection;
import com.mohammadzoubi.microservices.order.repository.projection.GetOrderProjection;
import com.mohammadzoubi.microservices.order.repository.projection.GetOrdersProjection;
import com.mohammadzoubi.microservices.order.repository.projection.GetOrdersWithItemsProjection;
import com.mohammadzoubi.microservices.order.util.OrderUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public CreateOrderResponse createOrder(
            final CreateOrderRequest request) {

        String orderNumber = OrderUtils.generateOrderNumber();

        Order order = new Order();
        order.setOrderNumber(orderNumber);
        order.setCustomerId(request.getCustomerId());
        order.setStatus(OrderStatusEnum.PENDING);

        for (Item itemRequest
                : request.getItems()) {
            OrderItem item = new OrderItem();
            item.setProductName(itemRequest.getProductName());
            item.setQuantity(itemRequest.getQuantity());
            item.setPrice(BigDecimal.valueOf(itemRequest.getPrice()));
            order.addOrderItem(item);
        }

        order.setTotalAmount(calculateTotalAmount(order));

        order = orderRepository.save(order);

        return CreateOrderResponse.builder()
                .orderid(order.getOrderId())
                .orderNumber(order.getOrderNumber())
                .build();
    }

    public void updateOrder(
            final Long orderId,
            final UpdateOrderRequest request) {

        orderRepository.updateOrder(orderId, request.getCustomerId());

    }

    public PageResult<GetAllOrdersResponse> getOrders(
            final String orderNumber,
            final Integer pageNumber,
            final Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber,
                pageSize,
                Sort.by(Sort.Direction.DESC,
                        SystemConfig.DEFAULT_ORDER_SORTING));

        Page<GetOrdersProjection> ordersProjections = orderRepository.getOrders(orderNumber,pageable);

        final GetAllOrdersResponse content = GetAllOrdersResponse.builder().orders(
                ordersProjections
                        .stream()
                        .map(p -> OrderDetails.builder()
                                .orderId(p.getOrderId())
                                .orderNumber(p.getOrderNumber())
                                .customerId(p.getCustomerId())
                                .status(p.getStatus())
                                .totalAmount(p.getTotalAmount())
                                .orderDate(p.getOrderDate())
                                .build()).toList()).build();

        return PageResult.<GetAllOrdersResponse>builder()
                .content(content)
                .page(ordersProjections.getNumber())
                .totalElements(ordersProjections.getTotalElements())
                .totalPages(ordersProjections.getTotalPages())
                .build();
    }

    public PageResult<GetAllOrdersWithItemsResponse> getOrdersWithItems(
            final Integer pageNumber,
            final Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber,
                pageSize,
                Sort.by(Sort.Direction.DESC,
                        SystemConfig.DEFAULT_ORDER_SORTING));

        Page<GetOrdersWithItemsProjection> ordersProjections = orderRepository.getOrdersWithItems(pageable);

        Map<Long, OrdersWithItems> orderMap = new LinkedHashMap<>();

        for (GetOrdersWithItemsProjection row : ordersProjections) {
            OrdersWithItems order = orderMap.computeIfAbsent(row.getOrderId(), id -> {
                return OrdersWithItems.builder()
                        .orderId(row.getOrderId())
                        .orderNumber(row.getOrderNumber())
                        .customerId(row.getCustomerId())
                        .status(row.getStatus())
                        .totalAmount(row.getTotalAmount())
                        .orderDate(row.getOrderDate())
                        .build();
            });
            order.getOrderItems().add(
                    OrderItems.builder()
                            .productName(row.getProductName())
                            .quantity(row.getQuantity())
                            .price(row.getPrice())
                            .totalPrice(row.getTotalPrice())
                            .build());


        }

        final GetAllOrdersWithItemsResponse content = GetAllOrdersWithItemsResponse.builder()
                .orders(new ArrayList<>(orderMap.values()))
                .build();

        return PageResult.<GetAllOrdersWithItemsResponse>builder()
                .content(content)
                .page(ordersProjections.getNumber())
                .totalElements(content.getOrders().size())
                .totalPages(ordersProjections.getTotalPages())
                .build();
    }

    public GetOrderResponse getOrderByOrderId(final Long orderId) {

        final GetOrderProjection getOrderProjection = orderRepository.getOrderByOrderId(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.ORDER_NOT_FOUND_ERROR_MESSAGE));


        return GetOrderResponse.builder()
                .orderId(getOrderProjection.getOrderId())
                .orderNumber(getOrderProjection.getOrderNumber())
                .customerId(getOrderProjection.getCustomerId())
                .status(getOrderProjection.getStatus())
                .totalAmount(getOrderProjection.getTotalAmount())
                .orderDate(getOrderProjection.getOrderDate())
                .build();
    }

    public PageResult<GetAllCustomerOrdersResponse> getOrdersByCustomerId(
            final Long customerId,
            final Integer pageNumber,
            final Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber,
                pageSize,
                Sort.by(Sort.Direction.DESC,
                        SystemConfig.DEFAULT_ORDER_SORTING));

        Page<GetOrdersProjection> ordersProjections = orderRepository.getOrdersByCustomerId(customerId, pageable);

        final GetAllCustomerOrdersResponse content = GetAllCustomerOrdersResponse.builder().orders(
                ordersProjections
                        .stream()
                        .map(p -> CustomerOrdersDetails.builder()
                                .orderId(p.getOrderId())
                                .orderNumber(p.getOrderNumber())
                                .status(p.getStatus())
                                .totalAmount(p.getTotalAmount())
                                .orderDate(p.getOrderDate())
                                .build()).toList()).build();

        return PageResult.<GetAllCustomerOrdersResponse>builder()
                .content(content)
                .totalElements(ordersProjections.getTotalElements())
                .page(ordersProjections.getNumber())
                .totalPages(ordersProjections.getTotalPages())
                .build();
    }

    public GetOrderItemsResponse getOrderItems(final Long orderId,
                                               final Integer pageNumber,
                                               final Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber,
                pageSize,
                Sort.by(Sort.Direction.DESC,
                        SystemConfig.DEFAULT_ORDER_ITEMS_SORTING));

        Page<GetOrderItemsProjection> getOrderItemsProjections =
                orderRepository.getOrderItems(orderId, pageable);

        return GetOrderItemsResponse.builder().items(
                getOrderItemsProjections
                        .stream()
                        .map(p -> OrderItemsDetails.builder()
                                .productName(p.getProductName())
                                .quantity(p.getQuantity())
                                .price(p.getPrice())
                                .totalPrice(p.getTotalPrice())
                                .build()).toList()).build();
    }

    public void updateOrderStatus(final Long orderId,
                                  final UpdateOrderStatusRequest request) {
        orderRepository.updateOrderStatus(orderId, request.getStatus().name());
    }

    public void deleteOrder(final Long orderId) {
        orderRepository.updateOrderStatus(orderId, OrderStatusEnum.DELETED.name());
    }

    private BigDecimal calculateTotalAmount(Order order) {
        return order.getOrderItems().stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


}
