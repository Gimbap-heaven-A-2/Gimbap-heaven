package com.sparta.gimbap_heaven.user.Entity;

import com.sparta.gimbap_heaven.common.entity.BaseTimeEntity;
import com.sparta.gimbap_heaven.user.dto.PasswordRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "userpassword")
public class UserPassword extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String changepassword;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public UserPassword(PasswordRequestDto requestDto,User user){
        this.changepassword=requestDto.getChangePassword();
        this.user=user;

    }


}
