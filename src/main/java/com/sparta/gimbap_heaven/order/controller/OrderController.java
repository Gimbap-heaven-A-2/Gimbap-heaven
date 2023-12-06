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

import static com.sparta.gimbap_heaven.global.constant.ResponseCode.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<SuccessResponse> saveInCart(@RequestBody @Valid BasketRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        orderService.saveInCart(requestDto, userDetails.getUser());
        return ResponseEntity.status(CREATE_BASKET_IN_CART.getHttpStatus()).body(new SuccessResponse(CREATE_BASKET_IN_CART));
    }

    @PutMapping
    public ResponseEntity<SuccessResponse> updateInCart(@RequestBody @Valid BasketRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        orderService.updateInCart(requestDto, userDetails.getUser());
        return ResponseEntity.status(UPDATE_BASKET_IN_CART.getHttpStatus()).body(new SuccessResponse(UPDATE_BASKET_IN_CART));
    }

    @DeleteMapping
    public ResponseEntity<SuccessResponse> deleteCart(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        orderService.deleteCart(userDetails.getUser());
        return ResponseEntity.status(DELETE_CART.getHttpStatus()).body(new SuccessResponse(DELETE_CART));
    }

    @DeleteMapping("/{menu_id}")
    public ResponseEntity<SuccessResponse> deleteMenuInCart(@PathVariable Long menu_id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        orderService.deleteMenuInCart(menu_id, userDetails.getUser());
        return ResponseEntity.status(DELETE_BASKET_IN_CART.getHttpStatus()).body(new SuccessResponse(DELETE_BASKET_IN_CART));
    }
}
