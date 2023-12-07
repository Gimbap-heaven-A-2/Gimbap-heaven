package com.sparta.gimbap_heaven.jwt;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional <RefreshToken> findByKeyUsername(String username);

    void deleteBykeyUsername(String username);



}
