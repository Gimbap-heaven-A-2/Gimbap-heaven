package com.sparta.gimbap_heaven.order.dto;

import com.sparta.gimbap_heaven.order.entity.Basket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasketResponseDto {

    private String name;
    private double price;
    private int count;

    public static BasketResponseDto of(Basket basket) {
        return new BasketResponseDto(basket.getMenu().getName(), basket.getPrice(), basket.getCount());
    }
}
