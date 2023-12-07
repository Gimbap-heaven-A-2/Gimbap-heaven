package com.sparta.gimbap_heaven.restaurant.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantRequestDto {
	@NotBlank
	private String restaurantName;

	@NotBlank
	private String restaurantNumber;

	@NotBlank
	private String restaurantAddress;
}
