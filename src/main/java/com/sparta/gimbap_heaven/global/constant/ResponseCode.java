package com.sparta.gimbap_heaven.global.constant;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {

	CREATE_MENU(OK,"메뉴 생성 완료"),
	UPDATE_MENU(OK,"메뉴 수정 완료"),
	DELETE_MENU(OK,"메뉴 삭제 완료"),
	SUCCESS_MENU(OK,"메뉴 조회 완료"),

	CREATE_BASKET_IN_CART(OK, "장바구니 담기 완료"),
	UPDATE_BASKET_IN_CART(OK, "장바구니 내 메뉴 수정 완료"),
	DELETE_BASKET_IN_CART(OK, "장바구니 내 메뉴 삭제 완료"),
	DELETE_CART(OK, "장바구니 전체 삭제 완료"),
	SUCCESS_BASKET_IN_CART(OK, "장바구니 내 메뉴 조회 완료");


	private final HttpStatus httpStatus;
	private final String message;
}
