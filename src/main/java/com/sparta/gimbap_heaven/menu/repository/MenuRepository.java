package com.sparta.gimbap_heaven.menu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sparta.gimbap_heaven.menu.entity.Menu;


@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
	List<Menu> findALlByCategory(String type);

}
