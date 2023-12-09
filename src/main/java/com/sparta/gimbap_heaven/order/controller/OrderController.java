package com.sparta.gimbap_heaven.order.controller;

import com.sparta.gimbap_heaven.global.dto.SuccessResponse;
import com.sparta.gimbap_heaven.order.dto.BasketRequestDto;
import com.sparta.gimbap_heaven.order.dto.OrderResponseDto;
import com.sparta.gimbap_heaven.order.service.OrderService;
import com.sparta.gimbap_heaven.security.UserDetailsImpl;
import com.sparta.gimbap_heaven.user.Entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sparta.gimbap_heaven.global.constant.ResponseCode.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // 장바구니 담기
    @PostMapping
    public ResponseEntity<SuccessResponse> saveInCart(@RequestBody @Valid BasketRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        orderService.saveInCart(requestDto, userDetails.getUser());
        return ResponseEntity.status(CREATE_BASKET_IN_CART.getHttpStatus()).body(new SuccessResponse(CREATE_BASKET_IN_CART));
    }

    // 장바구니 내 물건 개수 수정
    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse> updateInCart(@PathVariable Long id,
                                                        @RequestBody @Valid BasketRequestDto requestDto,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        orderService.updateInCart(id, requestDto, userDetails.getUser());
        return ResponseEntity.status(UPDATE_BASKET_IN_CART.getHttpStatus()).body(new SuccessResponse(UPDATE_BASKET_IN_CART));
    }

    // 장바구니 전체 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> deleteCart(@PathVariable Long id,
                                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        orderService.deleteCart(id, userDetails.getUser());
        return ResponseEntity.status(DELETE_CART.getHttpStatus()).body(new SuccessResponse(DELETE_CART));
    }

    // 장바구니 내 물건 삭제
    @DeleteMapping("/{order_id}/menu/{menu_id}")
    public ResponseEntity<SuccessResponse> deleteMenuInCart(@PathVariable Long order_id,
                                                            @PathVariable Long menu_id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        orderService.deleteMenuInCart(order_id, menu_id, userDetails.getUser());
        return ResponseEntity.status(DELETE_BASKET_IN_CART.getHttpStatus()).body(new SuccessResponse(DELETE_BASKET_IN_CART));
    }

    // 장바구니 내 물건 조회
    @GetMapping
    public ResponseEntity<?> getCart(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.status(SUCCESS_BASKET_IN_CART.getHttpStatus())
            .body(new SuccessResponse(SUCCESS_BASKET_IN_CART, orderService.getCart(userDetails.getUser())));
    }

    // 장바구니 주문
    @PutMapping("/{id}/ordered")
    public ResponseEntity<SuccessResponse> setCartIsOrdered(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        orderService.updateCartIsOrdered(id, userDetails.getUser());
        return ResponseEntity.status(DONE_ORDERED.getHttpStatus()).body(new SuccessResponse(DONE_ORDERED));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderedList(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<OrderResponseDto> responseList = orderService.getOrderedList(id, userDetails.getUser());
        return ResponseEntity.status(SUCCESS_ORDERS_IS_ORDERED.getHttpStatus()).body(new SuccessResponse(SUCCESS_ORDERS_IS_ORDERED, responseList));
    }
}
