package com.sparta.gimbap_heaven.order.dto;

import com.sparta.gimbap_heaven.order.entity.Basket;
import com.sparta.gimbap_heaven.order.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {

    private String username;
    private List<BasketResponseDto> basketResponseDtoList;
    private double totalPrice;

    public static OrderResponseDto of(Order order) {
        return OrderResponseDto.builder()
                .username(order.getUser().getUsername())
                .basketResponseDtoList(order.getBaskets().stream().map(BasketResponseDto::of).toList())
                .totalPrice(order.getTotalPrice())
                .build();
    }
}
