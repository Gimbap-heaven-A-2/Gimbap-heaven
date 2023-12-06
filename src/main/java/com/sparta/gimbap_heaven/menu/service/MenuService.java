package com.sparta.gimbap_heaven.menu.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.gimbap_heaven.menu.dto.MenuRequestDto;
import com.sparta.gimbap_heaven.menu.entity.Menu;
import com.sparta.gimbap_heaven.menu.repository.MenuRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MenuService {
	private final MenuRepository menuRepository;

	public MenuService(MenuRepository menuRepository) {
		this.menuRepository = menuRepository;
	}

	public void createMenu(MenuRequestDto menuRequestDto ){ //User user
		// checkUserRoleAdmin(user);
		Menu menu = new Menu(menuRequestDto);
		menuRepository.save(menu);
	}

	@Transactional
	public void updateMenu(Long id,MenuRequestDto menuRequestDto) throws IllegalAccessException { //User user
		//checkUserRoleAdmin(user)
		Menu menu = findMenu(id);
		menu.updateMenu(menuRequestDto);
	}


	public void deleteMenu(Long id) throws IllegalAccessException {
		//checkUserRoleAdmin(user)
		Menu menu = findMenu(id);
		menuRepository.delete(menu);
	}



	// public void checkUserRoleAdmin(User user){
	// 	 if(!user.getRole().equals(UserRole.ADMIN))
	// 		 throw new IllegalAccessException("메뉴를 수정할 권한이 없습니다.");
	// }

	public Menu findMenu(Long id) throws IllegalAccessException {
		return menuRepository.findById(id).orElseThrow(
			IllegalAccessException::new
		);
	}

}
