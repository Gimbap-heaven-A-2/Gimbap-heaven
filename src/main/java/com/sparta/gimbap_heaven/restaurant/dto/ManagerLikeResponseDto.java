package com.sparta.gimbap_heaven.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ManagerLikeResponseDto {

    private String restaurantName;
    private List<String> usernameList;
}
