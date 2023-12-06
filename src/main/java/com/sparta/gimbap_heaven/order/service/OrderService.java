package com.sparta.gimbap_heaven.order.service;

import com.sparta.gimbap_heaven.global.constant.ErrorCode;
import com.sparta.gimbap_heaven.global.exception.ApiException;
import com.sparta.gimbap_heaven.menu.entity.Menu;
import com.sparta.gimbap_heaven.menu.repository.MenuRepository;
import com.sparta.gimbap_heaven.order.dto.BasketRequestDto;
import com.sparta.gimbap_heaven.order.dto.OrderResponseDto;
import com.sparta.gimbap_heaven.order.entity.Basket;
import com.sparta.gimbap_heaven.order.entity.Order;
import com.sparta.gimbap_heaven.order.repository.BasketRepository;
import com.sparta.gimbap_heaven.order.repository.OrderRepository;
import com.sparta.gimbap_heaven.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final BasketRepository basketRepository;


    @Transactional
    public void saveInCart(BasketRequestDto requestDto, User user) {
        Order order = orderRepository.findByUserAndIsOrdered(user, false).orElseGet(
                () -> Order.builder().user(user).build()
        );

        Menu menu = menuRepository.findById(requestDto.getMenu_id()).orElseThrow(
                () -> new ApiException(ErrorCode.INVALID_MENU)
        );

        Basket basket = basketRepository.save(Basket.builder()
                .menu(menu)
                .order(order)
                .count(requestDto.getCount())
                .build());

        order.addBasket(basket);
        orderRepository.save(order);
    }
}
