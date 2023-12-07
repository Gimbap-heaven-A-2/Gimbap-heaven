package com.sparta.gimbap_heaven.menu.controller;

import static com.sparta.gimbap_heaven.global.constant.ResponseCode.*;

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

import com.sparta.gimbap_heaven.global.dto.SuccessResponse;
import com.sparta.gimbap_heaven.menu.dto.MenuRequestDto;
import com.sparta.gimbap_heaven.menu.dto.MenuResponseDto;
import com.sparta.gimbap_heaven.menu.service.MenuService;
import com.sparta.gimbap_heaven.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
public class MenuController {
	private final MenuService menuService;

	public MenuController(MenuService menuService) {
		this.menuService = menuService;
	}

	@PostMapping("/restaurant/{id}/menus")
	public ResponseEntity<SuccessResponse> createMenu(@PathVariable Long id,
													  @RequestBody MenuRequestDto menuRequestDto,
													  @AuthenticationPrincipal UserDetailsImpl userDetails) {
		menuService.createMenu(id ,menuRequestDto, userDetails.getUser());
		return ResponseEntity.status(CREATE_MENU.getHttpStatus().value())
			.body(new SuccessResponse(CREATE_MENU));
	}

	@GetMapping("/restaurant/{id}/menus")
	public ResponseEntity<SuccessResponse> getMenu(@PathVariable Long id){
		MenuResponseDto menuResponseDto = menuService.getAllMenu(id);
		return ResponseEntity.status(SUCCESS_MENU.getHttpStatus().value())
			.body(new SuccessResponse(SUCCESS_MENU,menuResponseDto));
	}

	@GetMapping("/restaurant/{id}/menus/type")
	public ResponseEntity<SuccessResponse> getTypeMenu(@PathVariable Long id,
													   @RequestParam(name = "type") String type){

		MenuResponseDto menuResponseDto = menuService.getFoodTypeMenu(id, type);

		return ResponseEntity.status(SUCCESS_MENU.getHttpStatus().value())
			.body(new SuccessResponse(SUCCESS_MENU,menuResponseDto));
	}


	@PutMapping("/restaurant/{restaurantId}/menus/{menusId}")
	public ResponseEntity<SuccessResponse> updateMenu(@PathVariable(name = "restaurantId") Long restaurantId,
													  @PathVariable(name = "menusId") Long menusId,
													  @RequestBody MenuRequestDto menuRequestDto,
													  @AuthenticationPrincipal UserDetailsImpl userDetails){
		menuService.updateMenu(restaurantId, menusId, menuRequestDto, userDetails.getUser());
		return ResponseEntity.status(UPDATE_MENU.getHttpStatus().value())
			.body(new SuccessResponse(UPDATE_MENU));
	}

	@DeleteMapping("/restaurant/{restaurantId}/menus/{menusId}")
	public ResponseEntity<SuccessResponse> deleteMenu(@PathVariable(name = "restaurantId")Long restaurantId,
													  @PathVariable(name = "menusId")Long menusId,
													  @AuthenticationPrincipal UserDetailsImpl userDetails){
		menuService.deleteMenu(restaurantId,menusId, userDetails.getUser());

		return ResponseEntity.status(DELETE_MENU.getHttpStatus().value())
			.body(new SuccessResponse(DELETE_MENU));
	}



}
