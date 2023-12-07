package com.sparta.gimbap_heaven.restaurant.service;

import static com.sparta.gimbap_heaven.global.constant.ErrorCode.*;

import org.springframework.stereotype.Service;

import com.sparta.gimbap_heaven.global.exception.ApiException;
import com.sparta.gimbap_heaven.restaurant.entity.Restaurant;
import com.sparta.gimbap_heaven.restaurant.repository.RestaurantRepository;

@Service
public class RestaurantService {
	private final RestaurantRepository restaurantRepository;

	public RestaurantService(RestaurantRepository restaurantRepository) {
		this.restaurantRepository = restaurantRepository;
	}

	public Restaurant findRestaurant(Long id){
		return restaurantRepository.findById(id).orElseThrow(
			()->new ApiException(INVALID_RESTAURANT));
	}

}
