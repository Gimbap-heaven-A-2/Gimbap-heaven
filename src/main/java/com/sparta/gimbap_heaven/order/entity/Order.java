package com.sparta.gimbap_heaven.order.entity;// package com.sparta.gimbap_heaven.order.entity;

import com.sparta.gimbap_heaven.common.entity.BaseTimeEntity;
import com.sparta.gimbap_heaven.restaurant.entity.Restaurant;
import com.sparta.gimbap_heaven.user.Entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor
public class Order extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Basket> baskets = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    private double totalPrice = 0.0;

    private Boolean isOrdered = false;

    public void addBasket(Basket basket) {
        this.baskets.add(basket);
        this.totalPrice += basket.getPrice();
    }

    public Order(User user, Restaurant restaurant) {
        this.user = user;
        this.restaurant = restaurant;
    }

    public void deleteBasket(Basket basket) {
        this.totalPrice -= basket.getPrice();
        this.baskets.remove(basket);
    }

    public void updateIsOrdered(boolean isOrdered) {
        this.isOrdered = isOrdered;
    }
}
