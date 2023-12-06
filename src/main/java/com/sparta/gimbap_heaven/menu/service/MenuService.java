package com.sparta.gimbap_heaven.menu.service;


import static com.sparta.gimbap_heaven.global.constant.ErrorCode.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.gimbap_heaven.global.exception.ApiException;
import com.sparta.gimbap_heaven.menu.dto.MenuRequestDto;
import com.sparta.gimbap_heaven.menu.dto.MenuResponseDto;
import com.sparta.gimbap_heaven.menu.entity.Menu;
import com.sparta.gimbap_heaven.menu.repository.MenuRepository;
import com.sparta.gimbap_heaven.user.Entity.User;
import com.sparta.gimbap_heaven.user.Entity.UserRoleEnum;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MenuService {
	private final MenuRepository menuRepository;

	public MenuService(MenuRepository menuRepository) {
		this.menuRepository = menuRepository;
	}

	public void createMenu(MenuRequestDto menuRequestDto, User user )  {
		checkUserRoleAdmin(user);
		Menu menu = new Menu(menuRequestDto);
		menuRepository.save(menu);
	}

	public MenuResponseDto getAllMenu(){
		return new MenuResponseDto(menuRepository.findAll());
	}
	public MenuResponseDto getFoodTypeMenu(String type){
		return new MenuResponseDto(menuRepository.findALlByCategory(type));
	}

	@Transactional
	public void updateMenu(Long id,MenuRequestDto menuRequestDto, User user)  {
		checkUserRoleAdmin(user);
		Menu menu = findMenu(id);
		menu.updateMenu(menuRequestDto);
	}


	public void deleteMenu(Long id, User user) {
		checkUserRoleAdmin(user);
		Menu menu = findMenu(id);
		menuRepository.delete(menu);
	}

	public void checkUserRoleAdmin(User user) {
		 if(!user.getRole().equals(UserRoleEnum.ADMIN))
			 throw new ApiException(INVALID_USER);
	}

	public Menu findMenu(Long id) {
		return menuRepository.findById(id).orElseThrow(
			()->  new ApiException(INVALID_MENU)
		);
	}

}
