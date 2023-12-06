package com.sparta.gimbap_heaven.jwt;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<List<RefreshToken>> findAllByKeyUsername(String username);
    boolean existsByKeyUsername(String username);
    void deleteByKeyUsername(String username);
}
