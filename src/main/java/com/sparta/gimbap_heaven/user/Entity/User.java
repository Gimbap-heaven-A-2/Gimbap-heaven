package com.sparta.gimbap_heaven.user.Entity;


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
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)  // USER -> USER그대로 저장
    private UserRoleEnum role;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String intro;

    @Column
    private double money;

    public User(String username, String password, String email, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public void updateMoney(double money){
        this.money = money;
    }

    public void useMoney(Double totalPrice) {
        this.money -= totalPrice;
    }

    public void updatePassword(PasswordRequestDto requestDto){
        this.password=requestDto.getPassword();
    }
}