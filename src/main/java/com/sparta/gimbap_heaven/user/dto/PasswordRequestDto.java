package com.sparta.gimbap_heaven.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordRequestDto {
    private String password;
    private String changePassword;
}
