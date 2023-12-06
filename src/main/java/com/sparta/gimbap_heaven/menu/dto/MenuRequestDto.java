package com.sparta.gimbap_heaven.menu.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MenuRequestDto {
	@NotBlank
	private final String name;

	@NotBlank
	private final Double price;

	@NotBlank
	private final String category;

}
