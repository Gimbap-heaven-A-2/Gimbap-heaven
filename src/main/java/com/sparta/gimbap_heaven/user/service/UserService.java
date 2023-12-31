package com.sparta.gimbap_heaven.user.service;

import com.sparta.gimbap_heaven.global.constant.ErrorCode;
import com.sparta.gimbap_heaven.global.exception.ApiException;
import com.sparta.gimbap_heaven.jwt.RefreshTokenRepository;
import com.sparta.gimbap_heaven.security.UserDetailsImpl;
import com.sparta.gimbap_heaven.user.Entity.Like;
import com.sparta.gimbap_heaven.user.Entity.User;
import com.sparta.gimbap_heaven.user.Entity.UserPassword;
import com.sparta.gimbap_heaven.user.Entity.UserRoleEnum;
import com.sparta.gimbap_heaven.user.dto.*;
import com.sparta.gimbap_heaven.user.repository.LikeRepository;
import com.sparta.gimbap_heaven.user.repository.UserPasswordRepository;
import com.sparta.gimbap_heaven.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final LikeRepository likeRepository;

    private final RefreshTokenRepository refreshTokenRepository;

    private final PasswordEncoder passwordEncoder;

    // ADMIN_TOKEN
    @Value("${jwt.adminToken}")
    private String ADMIN_TOKEN;

    // MANAGER_TOKEN
    @Value("${jwt.managerToken}")
    private String MANAGER_TOKEN;

    private final UserPasswordRepository userPasswordRepository;



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
        if (!(requestDto.getToken() == null)) {
            if (requestDto.getToken().equals(ADMIN_TOKEN)) {
                role = UserRoleEnum.ADMIN;
            } else if (requestDto.getToken().equals(MANAGER_TOKEN)) {
                role = UserRoleEnum.MANAGER;
            } else {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
        }

        // 사용자 등록
        User user = new User(username, password, email, role);
        userRepository.save(user);
        UserPassword userPassword = new UserPassword();
        userPassword.setUser(user);
        userPassword.setChangepassword(user.getPassword());
        userPasswordRepository.save(userPassword);
    }
    @Transactional
    public void logout(UserDetailsImpl userDetails) {
        // 사용자 이름으로 레포지토리에서 토큰조회
        refreshTokenRepository.findByKeyUsername(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("로그아웃 사용자입니다."));

        // 로그아웃 처리
        refreshTokenRepository.deleteBykeyUsername(userDetails.getUsername());
    }

    @Transactional
    public void putProfile(Long id, updateProfileRequestDto profileRequestDto, UserDetailsImpl userDetails) {
        // 사용자 ID로 레포지토리에서 사용자 정보 조회
        User userById = userRepository.findById(id)
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
    public double updateMoney(Long userId, UpdateMoneyRequestDto requestDto, User user) {
        User updateUser = userRepository.findById(userId).orElseThrow(()-> new ApiException(ErrorCode.INVALID_USER_CHECK));

        if(user.getUsername().equals(updateUser.getUsername())){
            double moneySum = updateUser.getMoney() + requestDto.getMoney();
            updateUser.updateMoney(moneySum);
            return moneySum;
        }
        else {
            throw new ApiException(ErrorCode.INVALID_MADE);
        }

    }

    public UserResponseDto findOneUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()->new ApiException(ErrorCode.INVALID_USER_CHECK));
        return new UserResponseDto(user);
    }

    public User findUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ApiException(ErrorCode.INVALID_USER));
    }

    @Transactional
    public void updatePassword(Long id, PasswordRequestDto requestDto, User user) {
        String password=requestDto.getPassword();
        String chPassword = passwordEncoder.encode(requestDto.getChangePassword());
        String changePassword=requestDto.getChangePassword();
        requestDto.setChangePassword(chPassword);


        User userCheck = userRepository.findById(id).orElseThrow(()->new ApiException(ErrorCode.INVALID_USER_CHECK));
        if(userCheck.getUsername().equals(user.getUsername())){   //유저이름이 맞는지 확인
            if(!passwordEncoder.matches(password,userCheck.getPassword())){
                throw new ApiException(ErrorCode.INVALID_PASSWORD);  //api 에러 만들기
            }

            List<UserPassword> userPwdList = userPasswordRepository.findAllByUserIdOrderByCreatedAt(userCheck.getId());

            if(!userPwdList.isEmpty()){  //값이 있으면


                for(UserPassword userPass : userPwdList){
                    if(passwordEncoder.matches(changePassword,userPass.getChangepassword())){
                        throw new ApiException(ErrorCode.INVALID_SUCCESS_PASSWORD);
                    }
                }

                if(userPwdList.size()==3){
                    UserPassword psw = userPwdList.get(0);
                    userPasswordRepository.delete(psw);
                }
                userCheck.updatePassword(requestDto);
                UserPassword userPsw= new UserPassword(requestDto,userCheck);
                userPasswordRepository.save(userPsw);
            }

            else {  //값이 없으면
                userCheck.updatePassword(requestDto);
                UserPassword userPsw= new UserPassword(requestDto,userCheck);
                userPasswordRepository.save(userPsw);
            }



        }
        else {
            throw new ApiException(ErrorCode.INVALID_MADE);
        }

    }


    public LikeResponseDto getLikeRestaurantList(Long id, User user) {
        User requestUser = userRepository.findById(id).orElseThrow(
                () -> new ApiException(ErrorCode.INVALID_USER_CHECK)
        );

        if (!user.getUsername().equals(requestUser.getUsername())) {
            throw new ApiException(ErrorCode.INVALID_USER);
        }

        List<Like> likes = likeRepository.findAllByUser(requestUser);
        List<String> restaurantList = new ArrayList<>();
        for (Like like : likes) {
            restaurantList.add(like.getRestaurant().getRestaurantName());
        }

        return new LikeResponseDto(requestUser.getUsername(), restaurantList);
    }

    public List<LikeResponseDto> getLikeRestaurantListByAdmin(User user) {
        checkUserIsAdmin(user);

        Map<String, List<String>> map = new HashMap<>();
        List<Like> likes = likeRepository.findAll();
        List<LikeResponseDto> responseDtos = new ArrayList<>();

        for (Like like : likes) {
            List<String> list = new ArrayList<>();

            if (map.containsKey(like.getUser().getUsername())) {
                list = map.get(like.getUser().getUsername());
            }

            list.add(like.getRestaurant().getRestaurantName());
            map.put(like.getUser().getUsername(), list);
        }

        for (String username : map.keySet()) {
            responseDtos.add(new LikeResponseDto(username, map.get(username)));
        }

        return responseDtos;
    }

    private void checkUserIsAdmin(User user) {
        if (!user.getRole().equals(UserRoleEnum.ADMIN)) {
            throw new ApiException(ErrorCode.INVALID_USER);
        }
    }

    public User findNameByUser(String username){
        return userRepository.findByUsername(username).orElseThrow(
            () -> new ApiException(ErrorCode.INVALID_MANAGER));
    }
}

