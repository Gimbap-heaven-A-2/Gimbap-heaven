package com.sparta.gimbap_heaven.review.dto;


import com.sparta.gimbap_heaven.review.entity.Review;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewResponseDto{
    private String reviewContent;
    private String username;
    private Long order_id;

    public ReviewResponseDto(Review review){
        this.reviewContent=review.getReviewcontent();
        this.order_id=review.getOrder().getId();
        this.username=review.getUser().getUsername();
    }

}
