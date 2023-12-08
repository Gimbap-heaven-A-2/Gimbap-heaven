package com.sparta.gimbap_heaven.user.repository;

import com.sparta.gimbap_heaven.restaurant.entity.Restaurant;
import com.sparta.gimbap_heaven.user.Entity.Like;
import com.sparta.gimbap_heaven.user.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    List<Like> findAllByUser(User user);

    List<Like> findAllByRestaurant(Restaurant restaurant);

    Optional<Like> findByUserAndRestaurant(User user, Restaurant restaurant);
}
