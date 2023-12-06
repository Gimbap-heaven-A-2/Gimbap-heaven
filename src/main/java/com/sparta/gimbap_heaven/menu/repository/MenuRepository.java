package com.sparta.gimbap_heaven.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.gimbap_heaven.menu.entity.Menu;

public interface MenuRepository extends JpaRepository<Menu, Long> {


}
