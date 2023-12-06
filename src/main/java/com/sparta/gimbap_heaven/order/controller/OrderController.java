package com.sparta.gimbap_heaven.order.controller;

import com.sparta.gimbap_heaven.global.constant.ResponseCode;
import com.sparta.gimbap_heaven.global.dto.SuccessResponse;
import com.sparta.gimbap_heaven.order.dto.BasketRequestDto;
import com.sparta.gimbap_heaven.order.service.OrderService;
import com.sparta.gimbap_heaven.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<SuccessResponse> saveInCart(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                      @RequestParam @Valid BasketRequestDto requestDto) {
        orderService.saveInCart(requestDto, userDetails.getUser());
        return ResponseEntity.status(ResponseCode.CREATE_IN_CART.getHttpStatus()).body(new SuccessResponse(ResponseCode.CREATE_IN_CART));
    }

}
