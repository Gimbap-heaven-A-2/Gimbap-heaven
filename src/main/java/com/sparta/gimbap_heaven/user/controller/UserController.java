package com.sparta.gimbap_heaven.user.controller;

import com.sparta.gimbap_heaven.global.dto.SuccessResponse;
import com.sparta.gimbap_heaven.security.UserDetailsImpl;
import com.sparta.gimbap_heaven.user.dto.*;
import com.sparta.gimbap_heaven.user.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;

import static com.sparta.gimbap_heaven.global.constant.ResponseCode.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/users/sign-up")
    public ResponseEntity<SuccessResponse> signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {

        userService.signup(signupRequestDto);
        return ResponseEntity.status(SUCCESS_SIGNUP.getHttpStatus()).body(new SuccessResponse(SUCCESS_SIGNUP));

    }

    @DeleteMapping("/auth/logout")
    public ResponseEntity<SuccessResponse> logout(@AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletResponse response) {
        Cookie cookie = new Cookie("refreshtoken", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        
        userService.logout(userDetails);
        return ResponseEntity.status(SUCCESS_LOGOUT.getHttpStatus()).body(new SuccessResponse(SUCCESS_LOGOUT));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<SuccessResponse> editProfile(
            @PathVariable Long id,
            @Valid @RequestBody updateProfileRequestDto profileRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
            ) {

        userService.putProfile(id, profileRequestDto, userDetails);
        return ResponseEntity.status(SUCCESS_EDITPROFILE.getHttpStatus()).body(new SuccessResponse(SUCCESS_EDITPROFILE));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<SuccessResponse> getOneUser(@PathVariable Long id) {
        UserResponseDto responseDto = userService.findOneUser(id);
        return ResponseEntity.status(SUCCESS_USER.getHttpStatus()).body(new SuccessResponse(SUCCESS_USER, responseDto));
    }

    @PutMapping("/users/{id}/money")
    public ResponseEntity<SuccessResponse> updateMoney(@PathVariable Long id,
                                                       @RequestBody UpdateMoneyRequestDto requestDto,
                                                       @AuthenticationPrincipal UserDetailsImpl userDetails) {
        int money = (int) userService.updateMoney(id, requestDto, userDetails.getUser());
        return ResponseEntity.status(UPDATE_MONEY.getHttpStatus()).body(new SuccessResponse(UPDATE_MONEY, " Money : " + money / 10 + "만 " + (money % 10) * 1000 + "원"));
    }

    @PutMapping("/users/{id}/password")
    public ResponseEntity<SuccessResponse> updatePassword(@PathVariable Long id,
                                                          @Valid @RequestBody PasswordRequestDto requestDto,
                                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.updatePassword(id, requestDto, userDetails.getUser());
        return ResponseEntity.status(UPDATE_SUCCESS_PASSWORD.getHttpStatus()).body(new SuccessResponse(UPDATE_SUCCESS_PASSWORD));

    }


    @GetMapping("/users/{id}/likes")
    public ResponseEntity<?> getLikesList(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        LikeResponseDto response = userService.getLikeRestaurantList(id, userDetails.getUser());
        return ResponseEntity.status(SUCCESS_LIKES_RESTAURANT_LIST.getHttpStatus()).body(new SuccessResponse(SUCCESS_LIKES_RESTAURANT_LIST, response));
    }

    @GetMapping("/users/likes/admin")
    public ResponseEntity<?> getLikesListByAdmin(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<LikeResponseDto> response = userService.getLikeRestaurantListByAdmin(userDetails.getUser());
        return ResponseEntity.status(SUCCESS_LIKES_RESTAURANT_LIST.getHttpStatus()).body(new SuccessResponse(SUCCESS_LIKES_RESTAURANT_LIST, response));
    }

}