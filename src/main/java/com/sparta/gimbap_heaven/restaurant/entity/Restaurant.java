package com.sparta.gimbap_heaven.restaurant.entity;

import java.util.ArrayList;
import java.util.List;

import com.sparta.gimbap_heaven.common.entity.BaseTimeEntity;
import com.sparta.gimbap_heaven.menu.entity.Menu;
import com.sparta.gimbap_heaven.restaurant.dto.RestaurantRequestDto;
import com.sparta.gimbap_heaven.user.Entity.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Menu> menus = new ArrayList<>();
	public Restaurant(RestaurantRequestDto restaurantRequestDto, User user) {
		this.restaurantAddress = restaurantRequestDto.getRestaurantAddress();
		this.restaurantName = restaurantRequestDto.getRestaurantName();
		this.restaurantNumber = restaurantRequestDto.getRestaurantNumber();
		this.user = user;
	}

	public void updateRestaurant(RestaurantRequestDto restaurantRequestDto){
		this.restaurantName = restaurantRequestDto.getRestaurantName();
		this.restaurantAddress = restaurantRequestDto.getRestaurantAddress();
		this.restaurantNumber = restaurantRequestDto.getRestaurantNumber();
	}
	public void addRestaurant(List<Menu> menus){
		for (Menu menu: menus) {
			menu.setRestaurant(this);
		}
		this.menus.addAll(menus);
	}



}
