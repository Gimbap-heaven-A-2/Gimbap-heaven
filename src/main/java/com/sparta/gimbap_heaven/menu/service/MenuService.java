package com.sparta.gimbap_heaven.menu.service;


import static com.sparta.gimbap_heaven.global.constant.ErrorCode.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.gimbap_heaven.global.exception.ApiException;
import com.sparta.gimbap_heaven.menu.dto.MenuRequestDto;
import com.sparta.gimbap_heaven.menu.dto.MenuResponseDto;
import com.sparta.gimbap_heaven.menu.entity.Menu;
import com.sparta.gimbap_heaven.menu.repository.MenuRepository;
import com.sparta.gimbap_heaven.restaurant.entity.Restaurant;
import com.sparta.gimbap_heaven.restaurant.service.RestaurantService;
import com.sparta.gimbap_heaven.user.Entity.User;
import com.sparta.gimbap_heaven.user.Entity.UserRoleEnum;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MenuService {
	private final MenuRepository menuRepository;

	private final RestaurantService restaurantService;

	public MenuService(MenuRepository menuRepository, RestaurantService restaurantService) {
		this.menuRepository = menuRepository;
		this.restaurantService = restaurantService;
	}

	public void createMenu(Long id, MenuRequestDto menuRequestDto, User user )  {
		Restaurant restaurant = restaurantService.findRestaurant(id);
		restaurantService.checkUserRoleAdmin(restaurant,user);
		Menu menu = new Menu(menuRequestDto ,restaurant);
		menuRepository.save(menu);
	}

	public MenuResponseDto getAllMenu(Long id){
		return new MenuResponseDto(menuRepository.findAllByRestaurantId(id)
			.orElseThrow(()-> new ApiException(INVALID_MENU)));
	}

	public MenuResponseDto getFoodTypeMenu(Long id, String type){
		return new MenuResponseDto(menuRepository.findAllByRestaurantIdAndCategory(id,type)
			.orElseThrow(()-> new ApiException(INVALID_MENU)));
	}

	@Transactional
	public void updateMenu(Long restaurantId, Long menusId, MenuRequestDto menuRequestDto, User user)  {
		Restaurant restaurant = restaurantService.findRestaurant(restaurantId);
		restaurantService.checkUserRoleAdmin(restaurant, user);

		Menu menu = menuRepository.findByIdAndRestaurantId(menusId,restaurantId)
			.orElseThrow(()-> new ApiException(INVALID_MENU));
		menu.updateMenu(menuRequestDto);
	}


	public void deleteMenu(Long restaurantId, Long menusId, User user) {
		Restaurant restaurant = restaurantService.findRestaurant(restaurantId);
		restaurantService.checkUserRoleAdmin(restaurant, user);

		Menu menu = menuRepository.findByIdAndRestaurantId(menusId, restaurantId)
			.orElseThrow(()-> new ApiException(INVALID_MENU));
		menuRepository.delete(menu);
	}


	public Menu findMenu(Long id) {
		return menuRepository.findById(id).orElseThrow(
			()->  new ApiException(INVALID_MENU)
		);
	}

}
