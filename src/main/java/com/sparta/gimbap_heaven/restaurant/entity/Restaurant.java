package com.sparta.gimbap_heaven.restaurant.entity;

import java.util.ArrayList;
import java.util.List;

import com.sparta.gimbap_heaven.common.entity.BaseTimeEntity;
import com.sparta.gimbap_heaven.menu.entity.Menu;
import com.sparta.gimbap_heaven.restaurant.dto.RestaurantRequestDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "restaurant")
public class Restaurant extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String restaurantName;

	@Column
	private String restaurantNumber;

	@Column
	private String restaurantAddress;

	@OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Menu> menus = new ArrayList<>();
	public Restaurant(RestaurantRequestDto restaurantRequestDto)
	{
		this.restaurantAddress =restaurantRequestDto.getRestaurantAddress();
		this.restaurantName = restaurantRequestDto.getRestaurantName();
		this.restaurantNumber = restaurantRequestDto.getRestaurantNumber();
	}



}
