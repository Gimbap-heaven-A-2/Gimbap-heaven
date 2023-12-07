package com.sparta.gimbap_heaven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GimbapHeavenApplication {

	public static void main(String[] args) {
		SpringApplication.run(GimbapHeavenApplication.class, args);
	}

}
