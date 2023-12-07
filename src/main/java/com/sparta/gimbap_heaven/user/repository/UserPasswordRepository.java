package com.sparta.gimbap_heaven.user.repository;

import com.sparta.gimbap_heaven.user.Entity.User;
import com.sparta.gimbap_heaven.user.Entity.UserPassword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserPasswordRepository extends JpaRepository<UserPassword, Long> {


    Optional<UserPassword> findByUserId(Long id);

    List<UserPassword> findAllByUserIdOrderByCreatedAt(Long id);
}

