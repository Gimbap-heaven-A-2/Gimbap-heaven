package com.sparta.gimbap_heaven.restaurant.controller;

import static com.sparta.gimbap_heaven.global.constant.ResponseCode.*;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sparta.gimbap_heaven.global.dto.SuccessResponse;
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
															@AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {

		restaurantService.createRestaurant(file,userDetails.getUser());
		return ResponseEntity.status(CREATE_RESTAURANT.getHttpStatus().value()).body(new SuccessResponse(CREATE_RESTAURANT));

	}

	@PutMapping("/restaurant/{id}")
	public ResponseEntity<SuccessResponse> updateRestaurant(@PathVariable Long id,
															@RequestBody RestaurantRequestDto restaurantRequestDto,
															@AuthenticationPrincipal UserDetailsImpl userDetails){
		restaurantService.updateRestaurant(id, restaurantRequestDto, userDetails.getUser());
		return ResponseEntity.status(UPDATE_RESTAURANT.getHttpStatus().value()).body(new SuccessResponse(UPDATE_RESTAURANT));
	}

	@DeleteMapping("/restaurant/{id}")
	public ResponseEntity<SuccessResponse> deleteRestaurant(@PathVariable Long id,
															@AuthenticationPrincipal UserDetailsImpl userDetails) {

		restaurantService.deleteRestaurant(id,userDetails.getUser());
		return ResponseEntity.status(DELETE_RESTAURANT.getHttpStatus().value()).body(new SuccessResponse(DELETE_RESTAURANT));
	}



}
