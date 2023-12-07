package com.sparta.gimbap_heaven.review.service;


import com.sparta.gimbap_heaven.global.constant.ErrorCode;
import com.sparta.gimbap_heaven.global.exception.ApiException;
import com.sparta.gimbap_heaven.order.entity.Order;
import com.sparta.gimbap_heaven.order.repository.OrderRepository;
import com.sparta.gimbap_heaven.review.repository.ReviewRepository;
import com.sparta.gimbap_heaven.review.dto.ReviewRequestDto;
import com.sparta.gimbap_heaven.review.dto.ReviewResponseDto;
import com.sparta.gimbap_heaven.review.entity.Review;
import com.sparta.gimbap_heaven.user.Entity.User;
import com.sparta.gimbap_heaven.user.Entity.UserRoleEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {


    private  final ReviewRepository reviewRepository;

    private final OrderRepository orderRepository;

    public ReviewService(ReviewRepository reviewRepository , OrderRepository orderRepository){
        this.reviewRepository=reviewRepository;
        this.orderRepository=orderRepository;
    }


    public ReviewResponseDto createReview(Long orderId,
        ReviewRequestDto requestDto,
        User user) {
        Order order = orderRepository.findByIdAndIsOrdered(orderId, true).orElseThrow(()-> new ApiException(ErrorCode.INVALID_ORDER));
        if(order.getUser().getUsername().equals(user.getUsername())){
            Review review= reviewRepository.save(new Review(requestDto,order,user));
            return new ReviewResponseDto(review);
        }
        else {
            throw new ApiException(ErrorCode.INVALID_MADE);
        }
    }

    public ReviewResponseDto findOneReview(Long id) {
        Review review = reviewRepository.findById(id).orElseThrow(()-> new ApiException(ErrorCode.INVALID_REVIEW));
        return new ReviewResponseDto(review);
    }


    public List<ReviewResponseDto> getReviews() {
        List<Review> list = reviewRepository.findAllByOrderByModifiedAtDesc();
        List<ReviewResponseDto> responseDtoList = new ArrayList<>();

        for(Review review : list){
            responseDtoList.add(new ReviewResponseDto(review));
        }
        return responseDtoList;
    }

    @Transactional
    public ReviewResponseDto updateReview(Long id, ReviewRequestDto requestDto, User user) {
        Review review = reviewRepository.findById(id).orElseThrow(()-> new ApiException(ErrorCode.INVALID_REVIEW));
        if(user.getUsername().equals(review.getUser().getUsername()) || user.getRole()== UserRoleEnum.ADMIN){
            review.update(requestDto);
            return new ReviewResponseDto(review);
        }
        else {
            throw new ApiException(ErrorCode.INVALID_MADE);
        }

    }




    public void deleteReview(Long id, User user){
        Review review = reviewRepository.findById(id).orElseThrow(()-> new ApiException(ErrorCode.INVALID_REVIEW));
        if (user.getUsername().equals(review.getUser().getUsername()) || user.getRole()== UserRoleEnum.ADMIN){
            reviewRepository.delete(review);
        }
        else {
            throw new ApiException(ErrorCode.INVALID_MADE);
        }
    }



}
