package com.sparta.gimbap_heaven.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LikeResponseDto {

    private String username;
    private List<String> restaurantNames;

}

