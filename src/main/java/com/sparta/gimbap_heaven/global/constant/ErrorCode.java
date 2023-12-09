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

    // Token Exception
    UNKNOWN_ERROR_NOT_EXIST_REFRESHTOKEN(HttpStatus.BAD_REQUEST, "리프레시 토큰이 존재하지 않습니다. 다시 로그인해주세요."),

    UNKNOWN_ERROR_NOT_EXIST_ACCESSTOKEN(HttpStatus.BAD_REQUEST, "액세스 토큰이 존재하지 않습니다. 다시 로그인해주세요."),

    EXPIRED_TOKEN(HttpStatus.BAD_REQUEST, "만료된 토큰입니다. 다시 로그인하세요."),

    ACCESS_DENIED(HttpStatus.BAD_REQUEST, "유효하지 못한 토큰입니다."),

    // System Exception
    INVALID_USER(HttpStatus.FORBIDDEN,"권한이 없습니다."),

    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "일치하는 토큰이 없습니다."),

    INVALID_PARAMETER(HttpStatus.BAD_REQUEST,"잘못된 입력값입니다."),

    INVALID_VALUE(HttpStatus.BAD_REQUEST,"잘못된 입력값입니다."),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러"),

    // Custom Exception
    SECURITY(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다"),

    INVALID_MENU(HttpStatus.NOT_FOUND,"일치하는 메뉴 정보가 없습니다."),

    ALREADY_EXIST_IN_CART(HttpStatus.BAD_REQUEST,"이미 장바구니에 담긴 메뉴입니다."),

    INVALID_CART(HttpStatus.NOT_FOUND,"장바구니가 비어있습니다."),

    INVALID_MENU_IN_CART(HttpStatus.NOT_FOUND,"장바구니에 해당 메뉴가 존재하지 않습니다."),

    INVALID_MONEY(HttpStatus.BAD_REQUEST, "소지금이 부족합니다. 충전해주세요."),

    INVALID_REVIEW(HttpStatus.NOT_FOUND,"일치하는 리뷰가 없습니다."),

    INVALID_ORDER(HttpStatus.NOT_FOUND,"일치하는 오더가 없습니다."),

    INVALID_MADE(HttpStatus.BAD_REQUEST,"작성자가 아닙니다"),

    INVALID_USER_CHECK(HttpStatus.BAD_REQUEST,"사용자가 아닙니다"),

    INVALID_RESTAURANT(HttpStatus.NOT_FOUND,"일치하는 식당이 없습니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST,"비밀번호가 일치하지 않습니다"),
    INVALID_SUCCESS_PASSWORD(HttpStatus.BAD_REQUEST,"일치하는 비밀번호가 있습니다"),

    DIFFERENT_RESTAURANT_IN_CART(HttpStatus.BAD_REQUEST, "동일한 가게의 메뉴만 담을 수 있습니다."),

    ALREADY_LIKE_RESTAURANT(HttpStatus.BAD_REQUEST, "이미 좋아하는 가게입니다."),
    NON_LIKES_RESTAURANT(HttpStatus.BAD_REQUEST, "좋아요 설정이 되어 있지 않은 가게입니다.");

    private final HttpStatus httpStatus;
    private final String message;


}
