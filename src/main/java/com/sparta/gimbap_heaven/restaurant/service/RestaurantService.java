package com.sparta.gimbap_heaven.restaurant.service;

import com.sparta.gimbap_heaven.global.exception.ApiException;
import com.sparta.gimbap_heaven.menu.entity.Menu;
import com.sparta.gimbap_heaven.restaurant.dto.AdminRestaurantResponseDto;
import com.sparta.gimbap_heaven.restaurant.dto.AllRestaurantResponseDto;
import com.sparta.gimbap_heaven.restaurant.dto.ManagerLikeResponseDto;
import com.sparta.gimbap_heaven.restaurant.dto.RestaurantRequestDto;
import com.sparta.gimbap_heaven.restaurant.entity.Restaurant;
import com.sparta.gimbap_heaven.restaurant.repository.RestaurantRepository;
import com.sparta.gimbap_heaven.user.Entity.Like;
import com.sparta.gimbap_heaven.user.Entity.User;
import com.sparta.gimbap_heaven.user.Entity.UserRoleEnum;
import com.sparta.gimbap_heaven.user.service.LikeService;
import com.sparta.gimbap_heaven.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static com.sparta.gimbap_heaven.global.constant.ErrorCode.INVALID_RESTAURANT;
import static com.sparta.gimbap_heaven.global.constant.ErrorCode.INVALID_USER;

@Service
@RequiredArgsConstructor
public class RestaurantService {
	private final RestaurantRepository restaurantRepository;
	private final UserService userService;
	private final LikeService likeService;


	public void createRestaurant(List<MultipartFile> files, User user) throws Exception {
		checkUserRoleAdmin(user);
		List<String[]> datas = separatingFile(files);
		for (String[] data: datas) {
			String managerName = data[0];

			User manager = userService.findNameByUser(managerName);
			RestaurantRequestDto restaurantRequestDto= restaurantSeparating(data);
			Restaurant restaurant = new Restaurant(restaurantRequestDto, manager);

			List<Menu> menus = menusSeparating(data);
			restaurant.addRestaurant(menus);

			restaurantRepository.save(restaurant);
		}
	}

	public AllRestaurantResponseDto getAllRestaurant(){
		List<Restaurant> restaurants = restaurantRepository.findAll();
		return new AllRestaurantResponseDto(restaurants);
	}

	public AdminRestaurantResponseDto getAdminRestaurant(Long id,User user) {
		Restaurant restaurant = findRestaurant(id);
		checkUserRoleAdmin(restaurant,user);
		return new AdminRestaurantResponseDto(restaurant);
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
	public List<String[]> separatingFile(List<MultipartFile> multipartFiles) throws IOException {
		List<String[]> datas = new ArrayList<>();
		for (MultipartFile file : multipartFiles) {
			BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
			String line;
			String[] data = new String[0];
			while ((line = br.readLine()) != null) {
				data = line.split(",");
			}
			datas.add(data);
		}
		return datas;
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

	@Transactional
	public void likeRestaurant(Long userId, Long restaurantId, User user) {
		Restaurant restaurant = findRestaurant(restaurantId);
		User saveUser = userService.findUser(userId);

		checkUserRole(saveUser, user);
		likeService.findLikeByUserAndRestaurantForSave(restaurant, saveUser);
	}

	@Transactional
	public void cancelLikeRestaurant(Long userId, Long restaurantId, User user) {
		Restaurant restaurant = findRestaurant(restaurantId);
		User canceluser = userService.findUser(userId);

		checkUserRole(canceluser, user);
		likeService.cancelLike(restaurant, canceluser);

	}

	public ManagerLikeResponseDto getLikesByAdmin(Long restaurantId, User user) {
		Restaurant restaurant = findRestaurant(restaurantId);
		checkUserRoleAdmin(restaurant, user);

		List<Like> likes = likeService.getListByRestaurant(restaurant);
		List<String> names = new ArrayList<>();
		for (Like like : likes) {
			names.add(like.getUser().getUsername());
		}

		return new ManagerLikeResponseDto(restaurant.getRestaurantName(), names);
	}

	private void checkUserRole(User saveUser, User user) {
		if (!saveUser.getUsername().equals(user.getUsername())) {
			throw new ApiException(INVALID_USER);
		}
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
