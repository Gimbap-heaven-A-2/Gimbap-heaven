package com.sparta.gimbap_heaven.restaurant.controller;

import static com.sparta.gimbap_heaven.global.constant.ResponseCode.*;

import java.io.IOException;
import java.util.List;

import com.sparta.gimbap_heaven.restaurant.dto.ManagerLikeResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sparta.gimbap_heaven.global.dto.SuccessResponse;
import com.sparta.gimbap_heaven.restaurant.dto.AdminRestaurantResponseDto;
import com.sparta.gimbap_heaven.restaurant.dto.AllRestaurantResponseDto;
import com.sparta.gimbap_heaven.restaurant.dto.RestaurantRequestDto;
import com.sparta.gimbap_heaven.restaurant.service.RestaurantService;
import com.sparta.gimbap_heaven.security.UserDetailsImpl;

@RestController
@RequestMapping("/api")
public class RestaurantController {

	private final RestaurantService restaurantService;

	public RestaurantController(RestaurantService restaurantService) {
		this.restaurantService = restaurantService;
	}

	@PostMapping("/restaurant")
	public ResponseEntity<SuccessResponse> createRestaurant(@RequestParam("file") List<MultipartFile> file,
															@AuthenticationPrincipal UserDetailsImpl userDetails) throws
		Exception {

		restaurantService.createRestaurant(file,userDetails.getUser());
		return ResponseEntity.status(CREATE_RESTAURANT.getHttpStatus().value())
			.body(new SuccessResponse(CREATE_RESTAURANT));

	}

	@GetMapping("/restaurant")
	public ResponseEntity<SuccessResponse> getRestaurant(){
		AllRestaurantResponseDto allRestaurantResponseDto = restaurantService.getAllRestaurant();
		return ResponseEntity.status(SUCCESS_RESTAURANT.getHttpStatus().value())
			.body(new SuccessResponse(SUCCESS_RESTAURANT,allRestaurantResponseDto));
	}

	@GetMapping("/restaurant/{id}/admin")
	public ResponseEntity<SuccessResponse> getAdminRestaurant(@PathVariable Long id,
															  @AuthenticationPrincipal UserDetailsImpl userDetails){
		AdminRestaurantResponseDto adminRestaurantResponseDto
			= restaurantService.getAdminRestaurant(id, userDetails.getUser());
		return ResponseEntity.status(ADMIN_SUCCESS_RESTAURANT.getHttpStatus().value())
			.body(new SuccessResponse(ADMIN_SUCCESS_RESTAURANT,adminRestaurantResponseDto));
	}

	@PutMapping("/restaurant/{id}")
	public ResponseEntity<SuccessResponse> updateRestaurant(@PathVariable Long id,
															@RequestBody RestaurantRequestDto restaurantRequestDto,
															@AuthenticationPrincipal UserDetailsImpl userDetails){
		restaurantService.updateRestaurant(id, restaurantRequestDto, userDetails.getUser());
		return ResponseEntity.status(UPDATE_RESTAURANT.getHttpStatus().value())
			.body(new SuccessResponse(UPDATE_RESTAURANT));
	}

	@DeleteMapping("/restaurant/{id}")
	public ResponseEntity<SuccessResponse> deleteRestaurant(@PathVariable Long id,
															@AuthenticationPrincipal UserDetailsImpl userDetails) {

		restaurantService.deleteRestaurant(id,userDetails.getUser());
		return ResponseEntity.status(DELETE_RESTAURANT.getHttpStatus().value())
			.body(new SuccessResponse(DELETE_RESTAURANT));
	}

	@PostMapping("/restaurant/{restaurant_id}/likes/{user_id}")
	public ResponseEntity<SuccessResponse> likeRestaurant(@PathVariable Long restaurant_id, @PathVariable Long user_id,
														  @AuthenticationPrincipal UserDetailsImpl userDetails) {

		restaurantService.likeRestaurant(user_id, restaurant_id, userDetails.getUser());
		return ResponseEntity.status(SUCCESS_LIKES_RESTAURANT.getHttpStatus()).body(new SuccessResponse(SUCCESS_LIKES_RESTAURANT));
	}

	@DeleteMapping("/restaurant/{restaurant_id}/likes/{user_id}")
	public ResponseEntity<SuccessResponse> cancelLikeRestaurant(@PathVariable Long restaurant_id, @PathVariable Long user_id,
																@AuthenticationPrincipal UserDetailsImpl userDetails) {
		restaurantService.cancelLikeRestaurant(user_id, restaurant_id, userDetails.getUser());
		return ResponseEntity.status(DELETE_LIKES_RESTAURANT.getHttpStatus()).body(new SuccessResponse(DELETE_LIKES_RESTAURANT));
	}

	@GetMapping("/restaurant/{restaurant_id}/likes/admin")
	public ResponseEntity<?> getLikesListByRestaurant(@PathVariable Long restaurant_id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		ManagerLikeResponseDto response = restaurantService.getLikesByAdmin(restaurant_id, userDetails.getUser());
		return ResponseEntity.status(SUCCESS_LIKES_USERS_LIST.getHttpStatus()).body(new SuccessResponse(SUCCESS_LIKES_RESTAURANT_LIST, response));
	}

}
