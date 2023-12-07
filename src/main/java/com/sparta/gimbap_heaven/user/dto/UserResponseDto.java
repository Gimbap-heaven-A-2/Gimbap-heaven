package com.sparta.gimbap_heaven.user.dto;

import com.sparta.gimbap_heaven.user.Entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
    private String username;
    private String email;
    private Integer money;
    private String intro;

    public UserResponseDto(User user){
        this.username=user.getUsername();
        this.email=user.getEmail();
        this.money=user.getMoney();
        this.intro=user.getIntro();
    }
}
