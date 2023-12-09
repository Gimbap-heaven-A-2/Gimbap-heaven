package com.sparta.gimbap_heaven.order.service;

import com.sparta.gimbap_heaven.global.constant.ErrorCode;
import com.sparta.gimbap_heaven.global.exception.ApiException;
import com.sparta.gimbap_heaven.menu.entity.Menu;
import com.sparta.gimbap_heaven.menu.service.MenuService;
import com.sparta.gimbap_heaven.order.dto.BasketRequestDto;
import com.sparta.gimbap_heaven.order.dto.OrderResponseDto;
import com.sparta.gimbap_heaven.order.entity.Basket;
import com.sparta.gimbap_heaven.order.entity.Order;
import com.sparta.gimbap_heaven.order.repository.BasketRepository;
import com.sparta.gimbap_heaven.order.repository.OrderRepository;
import com.sparta.gimbap_heaven.restaurant.entity.Restaurant;
import com.sparta.gimbap_heaven.restaurant.service.RestaurantService;
import com.sparta.gimbap_heaven.user.Entity.User;
import com.sparta.gimbap_heaven.user.Entity.UserRoleEnum;
import com.sparta.gimbap_heaven.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final BasketRepository basketRepository;

    private final MenuService menuService;
    private final UserService userService;
    private final RestaurantService restaurantService;


    @Transactional
    public void saveInCart(BasketRequestDto requestDto, User user) {
        Menu menu = menuService.findMenu(requestDto.getMenu_id());
        Restaurant restaurant = restaurantService.findRestaurant(menu.getRestaurant().getId());

        Order order = orderRepository.findByUserAndIsOrdered(user, false).orElseGet(
                () -> new Order(user, restaurant)
        );

        Basket basket = new Basket(order, menu, requestDto.getCount());
        if (order.getId() != null){
            if (basketRepository.findByOrderAndMenu_Id(order, menu.getId()).isPresent()) {
                throw new ApiException(ErrorCode.ALREADY_EXIST_IN_CART);
            }
            if (!Objects.equals(order.getRestaurant().getId(), restaurant.getId())) {
                throw new ApiException(ErrorCode.DIFFERENT_RESTAURANT_IN_CART);
            }
        }

        order.addBasket(basket);
        orderRepository.save(order);
    }

    @Transactional
    public void updateInCart(Long orderId, BasketRequestDto requestDto, User user) {
        Order order = orderRepository.findByIdAndIsOrdered(orderId, false).orElseThrow(
                () -> new ApiException(ErrorCode.INVALID_CART)
        );

        User orderUser = userService.findUser(order.getUser().getId());
        checkUserOrRole(user, orderUser);

        menuService.findMenu(requestDto.getMenu_id());

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

        User orderUser = userService.findUser(order.getUser().getId());
        checkUserOrRole(user, orderUser);

        orderRepository.delete(order);
    }

    @Transactional
    public void deleteMenuInCart(Long orderId, Long menuId, User user) {
        Order order = orderRepository.findByIdAndIsOrdered(orderId, false).orElseThrow(
                () -> new ApiException(ErrorCode.INVALID_CART)
        );

        User orderUser = userService.findUser(order.getUser().getId());
        checkUserOrRole(user, orderUser);

        menuService.findMenu(menuId);

        for (Basket basket : order.getBaskets()) {
            if (Objects.equals(basket.getMenu().getId(), menuId)) {
                order.deleteBasket(basket);
                basketRepository.delete(basket);

                if (order.getBaskets().isEmpty()) {
                    orderRepository.delete(order);
                }
                return;
            }
        }

        throw new ApiException(ErrorCode.INVALID_MENU_IN_CART);
    }

    public OrderResponseDto getCart(User user) {
        Order order = orderRepository.findByUserAndIsOrdered(user, false).orElseThrow(
                () -> new ApiException(ErrorCode.INVALID_CART)
        );

        User orderUser = userService.findUser(order.getUser().getId());
        checkUserOrRole(user, orderUser);

        return OrderResponseDto.of(order);
    }

    @Transactional
    public void updateCartIsOrdered(Long orderId, User user) {
        Order order = orderRepository.findByIdAndIsOrdered(orderId, false).orElseThrow(
                () -> new ApiException(ErrorCode.INVALID_CART)
        );

        if (order.getTotalPrice() > user.getMoney()) {
            throw new ApiException(ErrorCode.INVALID_MONEY);
        }

        User orderUser = userService.findUser(order.getUser().getId());
        checkUserOrRole(user, orderUser);

        User orderedUser = userService.findUser(user.getId());
        orderedUser.useMoney(order.getTotalPrice());

        Restaurant restaurant = order.getRestaurant();
        restaurant.saveMoney(order.getTotalPrice());

        order.updateIsOrdered(true);
    }

    public List<OrderResponseDto> getOrderedList(Long id, User user) {
        User findUser = userService.findUser(id);
        checkUserOrRole(user, findUser);

        List<Order> all = orderRepository.findAllByUserAndIsOrdered(findUser, true);

        return all.stream().map(OrderResponseDto::of).toList();
    }

    private static void checkUserOrRole(User loginUser, User orderUser) {
        if (!orderUser.getUsername().equals(loginUser.getUsername()) && !loginUser.getRole().equals(UserRoleEnum.ADMIN)) {
            throw new ApiException(ErrorCode.INVALID_USER);
        }
    }
}
