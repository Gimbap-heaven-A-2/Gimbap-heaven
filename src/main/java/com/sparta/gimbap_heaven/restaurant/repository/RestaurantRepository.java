package com.sparta.gimbap_heaven.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sparta.gimbap_heaven.restaurant.entity.Restaurant;


@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {



}
