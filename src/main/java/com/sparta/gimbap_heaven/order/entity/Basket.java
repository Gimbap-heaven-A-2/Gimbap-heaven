package com.sparta.gimbap_heaven.order.entity;

import com.sparta.gimbap_heaven.menu.entity.Menu;
import com.sparta.gimbap_heaven.order.dto.BasketRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Basket {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    private int count;

    public Basket(Order order, Menu menu, int count) {
        this.order = order;
        this.menu = menu;
        this.count = count;
    }

    public Double getPrice() {
        return menu.getPrice() * count;
    }

    public void updateCount(BasketRequestDto requestDto) {
        this.count = requestDto.getCount();
    }
}
