package com.sparta.gimbap_heaven.global.constant;

import org.springframework.http.HttpStatus;

import lombok.Generated;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Generated
public enum ErrorCode {
    // 예외처리를 위해 만든 클래스지만 잘 모릅니다.

    // System Exception
    INVALID_USER(HttpStatus.FORBIDDEN,"권한이 없습니다."),

    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "일치하는 토큰이 없습니다."),

    INVALID_PARAMETER(HttpStatus.BAD_REQUEST,"잘못된 입력값입니다."),

    INVALID_VALUE(HttpStatus.BAD_REQUEST,"잘못된 입력값입니다."),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러"),

    // Custom Exception
    SECURITY(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다"),

    INVALID_MENU(HttpStatus.BAD_REQUEST,"일치하는 메뉴 정보가 없습니다."),

    INVALID_CART(HttpStatus.BAD_REQUEST,"장바구니가 비어있습니다."),

    INVALID_MENU_IN_CART(HttpStatus.BAD_REQUEST,"장바구니에 해당 메뉴가 존재하지 않습니다."),

    INVALID_MONEY(HttpStatus.BAD_REQUEST, "소지금이 부족합니다. 충전해주세요."),

    INVALID_REVIEW(HttpStatus.BAD_REQUEST,"일치하는 리뷰가 없습니다."),
    INVALID_ORDER(HttpStatus.BAD_REQUEST,"일치하는 오더가 없습니다."),
    INVALID_MADE(HttpStatus.BAD_REQUEST,"작성자가 아닙니다"),
    INVALID_USER_CHECK(HttpStatus.BAD_REQUEST,"사용자가 아닙니다");

    private final HttpStatus httpStatus;
    private final String message;


}
