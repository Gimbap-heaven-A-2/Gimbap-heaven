package com.sparta.gimbap_heaven.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommonResponseDto {
    int statusCode;
    String message;
}
