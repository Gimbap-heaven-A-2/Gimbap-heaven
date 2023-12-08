package com.sparta.gimbap_heaven.user.service;

import com.sparta.gimbap_heaven.global.constant.ErrorCode;
import com.sparta.gimbap_heaven.global.exception.ApiException;
import com.sparta.gimbap_heaven.restaurant.entity.Restaurant;
import com.sparta.gimbap_heaven.user.Entity.Like;
import com.sparta.gimbap_heaven.user.Entity.User;
import com.sparta.gimbap_heaven.user.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;

    public void findLikeByUserAndRestaurantForSave(Restaurant restaurant, User user) {
        if (likeRepository.findByUserAndRestaurant(user, restaurant).isPresent()) {
            throw new ApiException(ErrorCode.ALREADY_LIKE_RESTAURANT);
        }

        likeRepository.save(new Like(user, restaurant));
    }

    public void cancelLike(Restaurant restaurant, User user) {
        Like like = likeRepository.findByUserAndRestaurant(user, restaurant).orElseThrow(
                () -> new ApiException(ErrorCode.NON_LIKES_RESTAURANT)
        );

        likeRepository.delete(like);
    }
}
