package com.sparta.gimbap_heaven.user.service;

import com.sparta.gimbap_heaven.security.UserDetailsImpl;
import com.sparta.gimbap_heaven.user.Entity.User;
import com.sparta.gimbap_heaven.user.Entity.UserRoleEnum;
import com.sparta.gimbap_heaven.user.dto.SignupRequestDto;
import com.sparta.gimbap_heaven.user.dto.UpdateMoneyRequestDto;
import com.sparta.gimbap_heaven.user.dto.UserResponseDto;
import com.sparta.gimbap_heaven.user.dto.updateProfileRequestDto;
import com.sparta.gimbap_heaven.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // ADMIN_TOKEN
    @Value("${jwt.adminToken}")
    private String ADMIN_TOKEN ;


    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // email 중복확인
        String email = requestDto.getEmail();
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 Email 입니다.");
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        // 사용자 등록
        User user = new User(username, password, email, role);
        userRepository.save(user);
    }

    @Transactional
    public void putProfile(Long user_id, updateProfileRequestDto profileRequestDto, UserDetailsImpl userDetails) {
        // 사용자 ID로 레포지토리에서 사용자 정보 조회
        User userById = userRepository.findById(user_id)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));


        // 사용자 이름으로 레포지토리에서 사용자 정보 조회
        User userByusername = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));


        if (userById.equals(userByusername)) {
            // 사용자 정보업데이트
            userById.setEmail(profileRequestDto.getEmail());
            userById.setIntro(profileRequestDto.getIntro());
        }

        // 사용자 정보 저장
        userRepository.save(userById);
    }
    @Transactional
    public void updateMoney(Long userId, UpdateMoneyRequestDto requestDto, User user) {
        User updateUser = userRepository.findById(userId).orElseThrow(()-> new IllegalArgumentException("사용자가 없어요"));

        if(user.getUsername().equals(updateUser.getUsername())){
            updateUser.updateMoney(requestDto);
        }
        else {
            throw new IllegalArgumentException("작성자가 일치하지 않습니다.");
        }

    }

    public UserResponseDto findOneUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("사용자가 없어요"));
        return new UserResponseDto(user);
    }
}

