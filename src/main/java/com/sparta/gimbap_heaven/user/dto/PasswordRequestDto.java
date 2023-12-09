package com.sparta.gimbap_heaven.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordRequestDto {
    private String password;
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$", message = "비밀번호는 8자 이상 15자 이하 영대소문자, 숫자만 가능합니다.")
    private String changePassword;
}
