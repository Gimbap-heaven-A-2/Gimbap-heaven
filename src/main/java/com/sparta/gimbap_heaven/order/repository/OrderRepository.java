package com.sparta.gimbap_heaven.order.repository;

import com.sparta.gimbap_heaven.order.entity.Order;
import com.sparta.gimbap_heaven.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByUserAndIsOrdered(User user, boolean isOrdered);
    Optional<Order> findByIdAndIsOrdered(Long id, boolean isOrdered);
}
