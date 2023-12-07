package com.sparta.gimbap_heaven.menu.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sparta.gimbap_heaven.menu.entity.Menu;


@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

	// 메뉴리포지토리에서 일치하는 식당 ID와 일치하는 Menu타입을 가져온다.
	Optional<List<Menu>> findAllByRestaurantIdAndCategory(Long id,String type);

	// 메뉴리포지토리에서 일치하는 식당 Id를 가져온다
	Optional<List<Menu>> findAllByRestaurantId(Long id);

	// 메뉴리포지토리에서 일치하는 메뉴아이디와 식당 Id를 가져온다.
	Optional<Menu> findByIdAndRestaurantId(Long menuId , Long restaurantId);


}
