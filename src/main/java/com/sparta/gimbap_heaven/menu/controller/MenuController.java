package com.sparta.gimbap_heaven.menu.controller;

import static com.sparta.gimbap_heaven.global.constant.ResponseCode.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.gimbap_heaven.global.constant.ResponseCode;
import com.sparta.gimbap_heaven.global.dto.SuccessResponse;
import com.sparta.gimbap_heaven.menu.dto.MenuRequestDto;
import com.sparta.gimbap_heaven.menu.service.MenuService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MenuController {
	private final MenuService menuService;

	@PutMapping("/menus")
	public ResponseEntity<SuccessResponse> createMenu(@RequestBody MenuRequestDto menuRequestDto){ //@AuthenticationPrincipal UserImpl

		menuService.createMenu(menuRequestDto); // userImpl.getUser();
		return ResponseEntity.status(CREATE_MENU.getHttpStatus()).body(new SuccessResponse(CREATE_MENU));
	}

	@PutMapping("/menus/{menuId}")
	public ResponseEntity<SuccessResponse> updateMenu(@PathVariable(name = "menuId") Long menuId,
													@RequestBody MenuRequestDto menuRequestDto) throws IllegalAccessException { //@AuthenticationPrincipal UserImpl
		menuService.updateMenu(menuId,menuRequestDto);
		return ResponseEntity.status(UPDATE_MENU.getHttpStatus()).body(new SuccessResponse(UPDATE_MENU));
	}

	@DeleteMapping("/menus/{menuId}")
	public ResponseEntity<SuccessResponse> deleteMenu(@PathVariable(name = "menuId")Long menuId) throws IllegalAccessException {//@AuthenticationPrincipal UserImpl
		menuService.deleteMenu(menuId);
		return ResponseEntity.status(DELETE_MENU.getHttpStatus()).body(new SuccessResponse(DELETE_MENU));
	}




}
