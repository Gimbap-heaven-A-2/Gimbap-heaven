package com.sparta.gimbap_heaven.restaurant.dto;

import com.sparta.gimbap_heaven.restaurant.entity.Restaurant;

import lombok.Getter;

@Getter
public class AdminRestaurantResponseDto {
	private final String restaurantName;

	private final String restaurantNumber;

	private final String restaurantAddress;

	private final Double restaurantMoney;

	private final String userName;

	public AdminRestaurantResponseDto(Restaurant restaurant) {
		this.restaurantName = restaurant.getRestaurantName();
		this.restaurantNumber = restaurant.getRestaurantNumber();
		this.restaurantAddress = restaurant.getRestaurantAddress();
		this.restaurantMoney = restaurant.getRestaurantMoney();
		this.userName = restaurant.getUser().getUsername();
	}
}
