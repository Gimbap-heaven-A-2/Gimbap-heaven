package com.sparta.gimbap_heaven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sparta.gimbap_heaven.menu.repository.MenuRepository;

import jakarta.annotation.PreDestroy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableJpaAuditing
@SpringBootApplication
public class GimbapHeavenApplication {

	public static void main(String[] args) {
		SpringApplication.run(GimbapHeavenApplication.class, args);
	}

}
