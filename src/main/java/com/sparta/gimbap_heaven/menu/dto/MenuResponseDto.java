package com.sparta.gimbap_heaven.menu.dto;

import java.util.List;

import com.sparta.gimbap_heaven.menu.entity.Menu;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class MenuResponseDto {

	private final List<GetFoodResponseDto> foodList;

	public MenuResponseDto(List<Menu> foods){
		this.foodList = foods.stream().map(GetFoodResponseDto::new).toList();
	}

}
