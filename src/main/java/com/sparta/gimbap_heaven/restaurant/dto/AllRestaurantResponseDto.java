package com.sparta.gimbap_heaven.restaurant.dto;

import java.util.List;

import com.sparta.gimbap_heaven.restaurant.entity.Restaurant;

import lombok.Getter;

@Getter
public class AllRestaurantResponseDto {
	private final List<RestaurantResponseDto> restaurantResponseDtos;

	public AllRestaurantResponseDto(List<Restaurant> restaurants) {
		this.restaurantResponseDtos = restaurants.stream().map(RestaurantResponseDto::new).toList();
	}
}
