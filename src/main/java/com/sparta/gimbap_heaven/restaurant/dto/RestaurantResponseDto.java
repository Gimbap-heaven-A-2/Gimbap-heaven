package com.sparta.gimbap_heaven.restaurant.dto;

import com.sparta.gimbap_heaven.restaurant.entity.Restaurant;

import lombok.Getter;

@Getter
public class RestaurantResponseDto {
	private final String restaurantName;

	private final String restaurantNumber;

	private final String restaurantAddress;

	private final String userName;

	public RestaurantResponseDto(Restaurant restaurant) {
		this.restaurantName = restaurant.getRestaurantName();
		this.restaurantNumber = restaurant.getRestaurantNumber();
		this.restaurantAddress = restaurant.getRestaurantAddress();
		this.userName = restaurant.getUser().getUsername();
	}

}
