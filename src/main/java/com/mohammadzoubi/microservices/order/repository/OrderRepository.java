package com.mohammadzoubi.microservices.order.repository;

import com.mohammadzoubi.microservices.order.entity.Order;
import com.mohammadzoubi.microservices.order.repository.projection.GetOrderItemsProjection;
import com.mohammadzoubi.microservices.order.repository.projection.GetOrderProjection;
import com.mohammadzoubi.microservices.order.repository.projection.GetOrdersProjection;
import com.mohammadzoubi.microservices.order.repository.projection.GetOrdersWithItemsProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {


    @Query(value = """
            SELECT o.order_id AS orderId,
                   o.customer_id AS customerId,
                   o.order_number AS OrderNumber,
                   o.status AS status,
                   o.total_amount AS totalAmount,
                   o.order_date AS orderDate,
                   oi.product_name AS productName,
                   oi.quantity AS quantity,
                   oi.price AS price,
                   oi.total_price AS totalPrice
            FROM orders o
            LEFT JOIN order_items oi on o.order_id = oi.order_id
            """, nativeQuery = true)
    Page<GetOrdersWithItemsProjection> getOrdersWithItems(Pageable pageable);

    @Query(value = """
            SELECT o.order_id AS orderId,
                   o.customer_id AS customerId,
                   o.order_number AS OrderNumber,
                   o.status AS status,
                   o.total_amount AS totalAmount,
                   o.order_date AS orderDate
            FROM orders o
            """, nativeQuery = true)
    Page<GetOrdersProjection> getOrders(Pageable pageable);

    @Query(value = """
            SELECT o.order_id AS orderId,
                   o.customer_id AS customerId,
                   o.order_number AS OrderNumber,
                   o.status AS status,
                   o.total_amount AS totalAmount,
                   o.order_date AS orderDate
            FROM orders o
            WHERE o.order_id= :orderId
            """, nativeQuery = true)
    Optional<GetOrderProjection> getOrderByOrderId(@Param("orderId") Long orderId);

    @Query(value = """
            SELECT o.order_id AS orderId,
                   o.customer_id AS customerId,
                   o.order_number AS OrderNumber,
                   o.status AS status,
                   o.total_amount AS totalAmount,
                   o.order_date AS orderDate
            FROM orders o
            WHERE o.customer_id = :customerId
            """, nativeQuery = true)
    Page<GetOrdersProjection> getOrdersByCustomerId(@Param("customerId") Long customerId,
                                                    Pageable pageable);

    @Query(value = """
            SELECT oi.product_name AS productName,
                   oi.quantity AS quantity,
                   oi.price AS price,
                   oi.total_price AS totalPrice
            FROM order_items oi
            WHERE oi.order_id = :orderId
            """, nativeQuery = true)
    Page<GetOrderItemsProjection> getOrderItems(@Param("orderId") Long orderId,
                                                Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE orders
            SET
             customer_id = CASE WHEN :customerId IS NOT NULL THEN :customerId ELSE customer_id END
            WHERE order_id = :orderId
            AND status = 'PENDING'""", nativeQuery = true)
    void updateOrder(@Param("orderId") Long orderId,
                     @Param("customerId") Long customerId);

    @Modifying
    @Transactional
    @Query(value = """ 
            UPDATE orders
            SET
             status = :status
            WHERE order_id = :orderId""", nativeQuery = true)
    void updateOrderStatus(@Param("orderId") Long orderId,
                           @Param("status") String status);
}
