package com.sparta.gimbap_heaven.order.dto;

import com.sparta.gimbap_heaven.menu.entity.Menu;
import com.sparta.gimbap_heaven.order.entity.Basket;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasketResponseDto {

    private String name;
    private double price;
    private int count;

    public static BasketResponseDto of(Basket basket) {
        return BasketResponseDto.builder()
                .name(basket.getMenu().getName())
                .price(basket.getMenu().getPrice())
                .count(basket.getCount())
                .build();
    }
}
