package com.mohammadzoubi.microservices.order.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long orderItemId;

    @Column(name = "product_name")
    private String productName;

    private Integer quantity;

    private BigDecimal price;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @PrePersist
    @PreUpdate
    public void calculateTotal() {
        if (price != null && quantity != null) {
            this.totalPrice = price.multiply(BigDecimal.valueOf(quantity));
        }
    }
}
