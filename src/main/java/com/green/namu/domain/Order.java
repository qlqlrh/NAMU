package com.green.namu.domain;

import com.green.namu.domain.status.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @CreationTimestamp
    @Column(name = "order_time", nullable = false, updatable = false)
    private LocalDateTime orderTime;

    @Column(name = "payment_method", nullable = false)
    private String paymentMethod; // 기본값 "현장 결제"

    @Column(name = "total_price", nullable = false)
    private int totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status; // UNORDERED, ORDERED, PICKED_UP

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderMenu> orderMenus = new ArrayList<>();

    public void setUser(User user) {
        this.user = user;
    }

    public void addOrderMenu(OrderMenu orderMenu) {
        this.orderMenus.add(orderMenu);
        orderMenu.setOrder(this);
    }
}
