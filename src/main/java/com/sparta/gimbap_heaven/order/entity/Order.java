package com.sparta.gimbap_heaven.order.entity;

import com.sparta.gimbap_heaven.common.entity.BaseTimeEntity;
import com.sparta.gimbap_heaven.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "orders")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Basket> baskets = new ArrayList<>();

    private Double totalPrice;
    private Boolean isOrdered = false;

    public void addBasket(Basket basket) {
        this.baskets.add(basket);
        this.totalPrice += basket.getPrice();
    }
}
