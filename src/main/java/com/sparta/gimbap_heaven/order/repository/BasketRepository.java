package com.sparta.gimbap_heaven.order.repository;

import com.sparta.gimbap_heaven.order.entity.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepository extends JpaRepository<Basket, Long> {
}
