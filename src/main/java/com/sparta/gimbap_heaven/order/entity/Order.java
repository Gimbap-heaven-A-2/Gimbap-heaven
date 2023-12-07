package com.sparta.gimbap_heaven.order.entity;// package com.sparta.gimbap_heaven.order.entity;
import java.util.ArrayList;
import java.util.List;

import com.sparta.gimbap_heaven.common.entity.BaseTimeEntity;

import com.sparta.gimbap_heaven.user.Entity.User;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor
public class Order extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Basket> baskets = new ArrayList<>();

    @Builder.Default
    private Double totalPrice = 0.0;

    @Builder.Default
    private Boolean isOrdered = false;

    public void addBasket(Basket basket) {
        this.baskets.add(basket);
        this.totalPrice += basket.getPrice();
    }

    public Order(User user) {
        this.user = user;
    }

    public void deleteBasket(Basket basket) {
        this.totalPrice -= basket.getPrice();
        this.baskets.remove(basket);
    }

    public void updateIsOrdered(boolean isOrdered) {
        this.isOrdered = isOrdered;
    }
}
