package com.sparta.gimbap_heaven.menu.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.gimbap_heaven.menu.dto.MenuRequestDto;
import com.sparta.gimbap_heaven.menu.dto.MenuResponseDto;
import com.sparta.gimbap_heaven.menu.entity.Menu;
import com.sparta.gimbap_heaven.menu.repository.MenuRepository;
import com.sparta.gimbap_heaven.user.User;
import com.sparta.gimbap_heaven.user.UserRoleEnum;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MenuService {
	private final MenuRepository menuRepository;

	public MenuService(MenuRepository menuRepository) {
		this.menuRepository = menuRepository;
	}

	public void createMenu(MenuRequestDto menuRequestDto, User user ){ //User user
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
	public void updateMenu(Long id,MenuRequestDto menuRequestDto, User user) throws IllegalAccessException { //User user
		checkUserRoleAdmin(user);
		Menu menu = findMenu(id);
		menu.updateMenu(menuRequestDto);
	}


	public void deleteMenu(Long id, User user) throws IllegalAccessException {
		checkUserRoleAdmin(user);
		Menu menu = findMenu(id);
		menuRepository.delete(menu);
	}

	public void checkUserRoleAdmin(User user){
		 if(!user.getRole().equals(UserRoleEnum.ADMIN))
			 throw new IllegalArgumentException("권한이 없습니다.");
	}

	public Menu findMenu(Long id) throws IllegalAccessException {
		return menuRepository.findById(id).orElseThrow(
			IllegalAccessException::new
		);
	}

}
