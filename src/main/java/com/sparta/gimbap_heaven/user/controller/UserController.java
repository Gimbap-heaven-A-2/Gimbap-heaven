package com.sparta.gimbap_heaven.user.controller;

import com.sparta.gimbap_heaven.global.dto.SuccessResponse;
import com.sparta.gimbap_heaven.security.UserDetailsImpl;
import com.sparta.gimbap_heaven.user.service.UserService;
import com.sparta.gimbap_heaven.user.dto.SignupRequestDto;
import com.sparta.gimbap_heaven.user.dto.updateProfileRequestDto;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/users/sign-up")
    public ResponseEntity<SuccessResponse> signup(@Valid @RequestBody SignupRequestDto signupRequestDto, BindingResult bindingResult) {
        // Validation 예외처리
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if(fieldErrors.size() > 0) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }
            throw new ValidationException("아이디, 비밀번호, 이메일 양식이 올바르지 않습니다.");
        }

        userService.signup(signupRequestDto);
        return ResponseEntity.ok(new SuccessResponse(200, "회원 가입 성공"));
    }

    @PutMapping("/users/{user_id}")
    public ResponseEntity<SuccessResponse> editProfile(
            @PathVariable Long user_id,
            @Valid @RequestBody updateProfileRequestDto profileRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            BindingResult bindingResult) {

        // Validation 예외처리
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if(fieldErrors.size() > 0) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }
            throw new ValidationException("이메일 양식이 올바르지 않습니다.");
        }

        userService.putProfile(user_id, profileRequestDto, userDetails);
        return ResponseEntity.ok(new SuccessResponse(200, "내 정보 수정 완료"));
    }

}