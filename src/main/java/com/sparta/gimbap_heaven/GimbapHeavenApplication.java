package com.sparta.gimbap_heaven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sparta.gimbap_heaven.menu.repository.MenuRepository;

import jakarta.annotation.PreDestroy;

@SpringBootApplication
public class GimbapHeavenApplication {

	public static void main(String[] args) {
		SpringApplication.run(GimbapHeavenApplication.class, args);
	}

}
