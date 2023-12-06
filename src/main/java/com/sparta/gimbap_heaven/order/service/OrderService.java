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

import java.util.Objects;

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

    @Transactional
    public void updateInCart(BasketRequestDto requestDto, User user) {
        Order order = orderRepository.findByUserAndIsOrdered(user, false).orElseThrow(
                () -> new ApiException(ErrorCode.INVALID_CART)
        );

        menuRepository.findById(requestDto.getMenu_id()).orElseThrow(
                () -> new ApiException(ErrorCode.INVALID_MENU)
        );

        for (Basket basket : order.getBaskets()) {
            if (Objects.equals(basket.getMenu().getId(), requestDto.getMenu_id())) {
                order.deleteBasket(basket);
                basket.updateCount(requestDto);
                order.addBasket(basket);
                return;
            }
        }

        throw new ApiException(ErrorCode.INVALID_MENU_IN_CART);
    }

    @Transactional
    public void deleteCart(User user) {
        Order order = orderRepository.findByUserAndIsOrdered(user, false).orElseThrow(
                () -> new ApiException(ErrorCode.INVALID_CART)
        );

        orderRepository.delete(order);
    }

    @Transactional
    public void deleteMenuInCart(Long menuId, User user) {
        Order order = orderRepository.findByUserAndIsOrdered(user, false).orElseThrow(
                () -> new ApiException(ErrorCode.INVALID_CART)
        );

        menuRepository.findById(menuId).orElseThrow(
                () -> new ApiException(ErrorCode.INVALID_MENU)
        );

        for (Basket basket : order.getBaskets()) {
            if (Objects.equals(basket.getMenu().getId(), menuId)) {
                order.deleteBasket(basket);
                basketRepository.delete(basket);
                return;
            }
        }

        throw new ApiException(ErrorCode.INVALID_MENU_IN_CART);
    }
}
