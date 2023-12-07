package com.sparta.gimbap_heaven.review.controller;


import com.sparta.gimbap_heaven.global.dto.SuccessResponse;
import com.sparta.gimbap_heaven.review.dto.ReviewRequestDto;
import com.sparta.gimbap_heaven.review.dto.ReviewResponseDto;
import com.sparta.gimbap_heaven.review.service.ReviewService;
import com.sparta.gimbap_heaven.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sparta.gimbap_heaven.global.constant.ResponseCode.*;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {


    private final ReviewService reviewService;





    @PostMapping("/order/{id}")
    public ResponseEntity<SuccessResponse> createReview(@PathVariable Long id,
                                                        @RequestBody ReviewRequestDto requestDto,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails){
        ReviewResponseDto responseDto= reviewService.createReview(id,requestDto,userDetails.getUser());
        return ResponseEntity.status(CREATE_REVIEW.getHttpStatus()).body(new SuccessResponse(CREATE_REVIEW,responseDto));
    }


    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse> getOneReview(@PathVariable Long id){
        ReviewResponseDto responseDto = reviewService.findOneReview(id);
        return ResponseEntity.status(SUCCESS_REVIEW.getHttpStatus()).body(new SuccessResponse(SUCCESS_REVIEW,responseDto));
    }


    @GetMapping("")
    public ResponseEntity<List<ReviewResponseDto>> getReviews(){
        return ResponseEntity.ok().body(reviewService.getReviews());
    }


    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse> updateReview(@PathVariable Long id,
                                                  @RequestBody ReviewRequestDto requestDto,
                                                  @AuthenticationPrincipal UserDetailsImpl userDetails){
        ReviewResponseDto responseDto = reviewService.updateReview(id,requestDto,userDetails.getUser());
        return ResponseEntity.status(UPDATE_REVIEW.getHttpStatus()).body(new SuccessResponse(UPDATE_REVIEW,responseDto));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> deleteReview(@PathVariable Long id,
                                                  @AuthenticationPrincipal UserDetailsImpl userDetails){
        reviewService.deleteReview(id, userDetails.getUser());
        return ResponseEntity.status(DELETE_REVIEW.getHttpStatus()).body(new SuccessResponse(DELETE_REVIEW));
    }
}
