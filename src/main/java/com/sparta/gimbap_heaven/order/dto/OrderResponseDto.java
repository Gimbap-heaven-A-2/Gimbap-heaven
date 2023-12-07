package com.sparta.gimbap_heaven.order.dto;

import com.sparta.gimbap_heaven.order.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OrderResponseDto {

    private String username;
    private String restaurantName;
    private List<BasketResponseDto> basketResponseDtoList;
    private double totalPrice;

    public static OrderResponseDto of(Order order) {
        List<BasketResponseDto> basket = order.getBaskets().stream().map(BasketResponseDto::of).toList();
        return new OrderResponseDto(order.getUser().getUsername(), order.getRestaurant().getRestaurantName(), basket, order.getTotalPrice());
    }
}
