package com.sparta.gimbap_heaven.menu.entity;

import com.sparta.gimbap_heaven.common.entity.BaseTimeEntity;
import com.sparta.gimbap_heaven.menu.dto.MenuRequestDto;
import com.sparta.gimbap_heaven.restaurant.entity.Restaurant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "menu")
public class Menu extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String category;

	@Column
	private String name;

	@Column
	private double price;

	@ManyToOne
	@JoinColumn(name = "restaurant_id")
	private Restaurant restaurant;

	public Menu(String category, String name, double price) {
		this.category = category;
		this.name = name;
		this.price = price;
	}

	public Menu(MenuRequestDto menuRequestDto, Restaurant restaurant){
		this.restaurant = restaurant;
		this.category = menuRequestDto.getCategory();
		this.price = menuRequestDto.getPrice();
		this.name = menuRequestDto.getName();
	}

	public void updateMenu(MenuRequestDto menuRequestDto){
		this.category = menuRequestDto.getCategory();
		this.price = menuRequestDto.getPrice();
		this.name = menuRequestDto.getName();
	}



}
