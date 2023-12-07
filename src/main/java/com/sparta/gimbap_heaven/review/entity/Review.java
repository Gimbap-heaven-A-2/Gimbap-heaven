package com.sparta.gimbap_heaven.review.entity;



import com.sparta.gimbap_heaven.common.entity.BaseTimeEntity;
import com.sparta.gimbap_heaven.order.entity.Order;
import com.sparta.gimbap_heaven.review.dto.ReviewRequestDto;
import com.sparta.gimbap_heaven.user.Entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Setter
@Getter
@Table(name="review")
public class Review extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String reviewcontent;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    public Review(ReviewRequestDto requestDto, Order order, User user){
        this.reviewcontent=requestDto.getReviewContent();
        this.order=order;
        this.user=user;
    }
    public void update(ReviewRequestDto requestDto){
        reviewcontent=requestDto.getReviewContent();
    }

}
