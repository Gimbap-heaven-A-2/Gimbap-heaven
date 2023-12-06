package com.sparta.gimbap_heaven.menu.dto;

import com.sparta.gimbap_heaven.menu.entity.Menu;

import lombok.Getter;

@Getter
public class GetFoodResponseDto {
	private final String foodCategory;
	private final Double price;
	private final String foodName;


	public GetFoodResponseDto(Menu menu){
		this.foodCategory = menu.getCategory();
		this.price = menu.getPrice();
		this.foodName = menu.getName();
	}

}
