package com.sparta.gimbap_heaven.restaurant.controller;

import com.sparta.gimbap_heaven.global.dto.SuccessResponse;
import com.sparta.gimbap_heaven.restaurant.dto.AdminRestaurantResponseDto;
import com.sparta.gimbap_heaven.restaurant.dto.AllRestaurantResponseDto;
import com.sparta.gimbap_heaven.restaurant.dto.RestaurantRequestDto;
import com.sparta.gimbap_heaven.restaurant.service.RestaurantService;
import com.sparta.gimbap_heaven.security.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.sparta.gimbap_heaven.global.constant.ResponseCode.*;

@RestController
@RequestMapping("/api")
public class RestaurantController {

	private final RestaurantService restaurantService;

	public RestaurantController(RestaurantService restaurantService) {
		this.restaurantService = restaurantService;
	}

	@PostMapping("/restaurant")
	public ResponseEntity<SuccessResponse> createRestaurant(@RequestParam("file") List<MultipartFile> file,
															@AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {

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



}
