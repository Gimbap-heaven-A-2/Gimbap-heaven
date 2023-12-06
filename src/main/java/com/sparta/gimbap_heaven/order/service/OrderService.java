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

import com.sparta.gimbap_heaven.user.Entity.User;
import com.sparta.gimbap_heaven.user.Entity.UserRoleEnum;


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
    public void updateInCart(Long orderId, BasketRequestDto requestDto, User user) {
        Order order = orderRepository.findByIdAndIsOrdered(orderId, false).orElseThrow(
                () -> new ApiException(ErrorCode.INVALID_CART)
        );

        checkUserOrRole(user, order);

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
    public void deleteCart(Long orderId, User user) {
        Order order = orderRepository.findByIdAndIsOrdered(orderId, false).orElseThrow(
                () -> new ApiException(ErrorCode.INVALID_CART)
        );

        checkUserOrRole(user, order);

        orderRepository.delete(order);
    }

    @Transactional
    public void deleteMenuInCart(Long orderId, Long menuId, User user) {
        Order order = orderRepository.findByIdAndIsOrdered(orderId, false).orElseThrow(
                () -> new ApiException(ErrorCode.INVALID_CART)
        );
        checkUserOrRole(user, order);

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

    public OrderResponseDto getCart(User user) {
        Order order = orderRepository.findByUserAndIsOrdered(user, false).orElseThrow(
                () -> new ApiException(ErrorCode.INVALID_CART)
        );

        checkUser(user, order);

        return OrderResponseDto.of(order);
    }

    @Transactional
    public void updateCartIsOrdered(Long orderId, User user) {
        Order order = orderRepository.findByUserAndIsOrdered(user, false).orElseThrow(
                () -> new ApiException(ErrorCode.INVALID_CART)
        );

        checkUser(user, order);

        order.updateIsOrdered(true);
    }

    private static void checkUserOrRole(User user, Order order) {
        if (!order.getUser().getUsername().equals(user.getUsername()) || !user.getRole().equals(UserRoleEnum.ADMIN)) {
            throw new ApiException(ErrorCode.INVALID_AUTHORIZATION);
        }
    }

    private void checkUser(User user, Order order) {
        if (!order.getUser().getUsername().equals(user.getUsername())) {
            throw new ApiException(ErrorCode.INVALID_AUTHORIZATION);
        }
    }
}
