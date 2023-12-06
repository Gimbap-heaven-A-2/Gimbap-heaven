package com.sparta.gimbap_heaven.order.controller;

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

    @PutMapping("/{order_id}")
    public ResponseEntity<SuccessResponse> updateInCart(@PathVariable Long order_id,
                                                        @RequestBody @Valid BasketRequestDto requestDto,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        orderService.updateInCart(order_id, requestDto, userDetails.getUser());
        return ResponseEntity.status(UPDATE_BASKET_IN_CART.getHttpStatus()).body(new SuccessResponse(UPDATE_BASKET_IN_CART));
    }

    @DeleteMapping("/{order_id}")
    public ResponseEntity<SuccessResponse> deleteCart(@PathVariable Long order_id,
                                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        orderService.deleteCart(order_id, userDetails.getUser());
        return ResponseEntity.status(DELETE_CART.getHttpStatus()).body(new SuccessResponse(DELETE_CART));
    }

    @DeleteMapping("/{order_id}/{menu_id}")
    public ResponseEntity<SuccessResponse> deleteMenuInCart(@PathVariable Long order_id,
                                                            @PathVariable Long menu_id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        orderService.deleteMenuInCart(order_id, menu_id, userDetails.getUser());
        return ResponseEntity.status(DELETE_BASKET_IN_CART.getHttpStatus()).body(new SuccessResponse(DELETE_BASKET_IN_CART));
    }

    @GetMapping
    public ResponseEntity<?> getCart(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.status(SUCCESS_BASKET_IN_CART.getHttpStatus()).body(new SuccessResponse(SUCCESS_BASKET_IN_CART, orderService.getCart(userDetails.getUser())));
    }

    @PutMapping("/{order_id}/ordered")
    public ResponseEntity<SuccessResponse> setCartIsOrdered(@PathVariable Long order_id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        orderService.updateCartIsOrdered(order_id, userDetails.getUser());
        return ResponseEntity.status(DONE_ORDERED.getHttpStatus()).body(new SuccessResponse(DONE_ORDERED));
    }
}
