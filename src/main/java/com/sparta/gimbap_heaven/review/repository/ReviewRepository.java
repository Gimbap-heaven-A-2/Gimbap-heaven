package com.sparta.gimbap_heaven.review.repository;

import com.sparta.gimbap_heaven.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    List<Review> findAllByOrderByModifiedAtDesc();

}
