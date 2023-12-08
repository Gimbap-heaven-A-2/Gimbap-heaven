package com.sparta.gimbap_heaven.restaurant.service;

import static com.sparta.gimbap_heaven.global.constant.ErrorCode.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sparta.gimbap_heaven.global.exception.ApiException;
import com.sparta.gimbap_heaven.menu.entity.Menu;
import com.sparta.gimbap_heaven.restaurant.dto.RestaurantRequestDto;
import com.sparta.gimbap_heaven.restaurant.entity.Restaurant;
import com.sparta.gimbap_heaven.restaurant.repository.RestaurantRepository;
import com.sparta.gimbap_heaven.user.Entity.User;
import com.sparta.gimbap_heaven.user.Entity.UserRoleEnum;
import com.sparta.gimbap_heaven.user.service.UserService;

@Service
public class RestaurantService {
	private final RestaurantRepository restaurantRepository;
	private final UserService userService;

	public RestaurantService(RestaurantRepository restaurantRepository, UserService userService) {
		this.restaurantRepository = restaurantRepository;
		this.userService = userService;
	}

	public void createRestaurant(List<MultipartFile> files, User user) throws IOException {
		checkUserRoleAdmin(user);
		String[] data = separatingFile(files);
		String managerName = data[0];

		User mabagerUser = userService.findNameByUser(managerName);
		RestaurantRequestDto restaurantRequestDto= restaurantSeparating(data);
		Restaurant restaurant = new Restaurant(restaurantRequestDto, mabagerUser);

		List<Menu> menus = menusSeparating(data);
		restaurant.addRestaurant(menus);

		restaurantRepository.save(restaurant);
	}


	@Transactional
	public void updateRestaurant(Long id, RestaurantRequestDto restaurantRequestDto, User user){
		Restaurant restaurant = findRestaurant(id);
		checkUserRoleAdmin(restaurant,user);
		restaurant.updateRestaurant(restaurantRequestDto);
	}

	public void deleteRestaurant(Long id, User user){
		Restaurant restaurant = findRestaurant(id);
		checkUserRoleAdmin(restaurant, user);
		restaurantRepository.delete(restaurant);
	}

	// IOE글로벌 Exception 추가
	public String[] separatingFile(List<MultipartFile> multipartFiles) throws IOException {
		String[] data = new String[0];
		for (MultipartFile file : multipartFiles) {
			BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				data = line.split(",");
			}
		}
		return data;
	}

	// 식당 데이터 분리
	public RestaurantRequestDto restaurantSeparating(String[] data){
		RestaurantRequestDto restaurantRequestDto = null;
		for (int i = 1; i < 4; i++) {
			restaurantRequestDto = new RestaurantRequestDto(data[1],data[2],data[3]);
		}
		return restaurantRequestDto;
	}

	// 메뉴 데이터 분리
	public List<Menu> menusSeparating(String[] data){
		List<Menu> menus = new ArrayList<>();
		for (int i = 4; i < data.length ; i++) {
			if(i%3 == 0) {
				Menu menu = new Menu(data[i - 2], data[i - 1], Double.parseDouble(data[i]));
				menus.add(menu);
			}
		}
		return menus;
	}


	public Restaurant findRestaurant(Long id){
		return restaurantRepository.findById(id).orElseThrow(
			()->new ApiException(INVALID_RESTAURANT));
	}

	public void checkUserRoleAdmin(User user) {
		if(!user.getRole().equals(UserRoleEnum.ADMIN))
			throw new ApiException(INVALID_USER);
	}

	public void checkUserRoleAdmin(Restaurant restaurant, User user){
		if((!user.getUsername().equals(restaurant.getUser().getUsername()))
			&& (!user.getRole().equals(UserRoleEnum.ADMIN)))
			throw new ApiException(INVALID_USER);
	}

}
