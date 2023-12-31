package com.sparta.gimbap_heaven.global.constant;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {

	SUCCESS_REISSUANCETOKEN(OK, "토큰이 재발급되었습니다."),
	SUCCESS_SIGNUP(OK, "회원가입이 성공하였습니다,"),
	SUCCESS_LOGOUT(OK, "로그아웃이 성공하였습니다."),
	SUCCESS_EDITPROFILE(OK, "내정보 수정이 성공하였습니다."),

	CREATE_MENU(OK,"메뉴 생성 완료"),
	UPDATE_MENU(OK,"메뉴 수정 완료"),
	DELETE_MENU(OK,"메뉴 삭제 완료"),
	SUCCESS_MENU(OK,"메뉴 조회 완료"),

	CREATE_BASKET_IN_CART(OK, "장바구니 담기 완료"),
	UPDATE_BASKET_IN_CART(OK, "장바구니 내 메뉴 수정 완료"),
	DELETE_BASKET_IN_CART(OK, "장바구니 내 메뉴 삭제 완료"),
	DELETE_CART(OK, "장바구니 전체 삭제 완료"),
	SUCCESS_BASKET_IN_CART(OK, "장바구니 조회 완료"),
	DONE_ORDERED(OK, "주문 완료"),
	SUCCESS_ORDERS_IS_ORDERED(OK, "주문 목록 조회 완료"),

	CREATE_REVIEW(OK,"리뷰 생성 완료"),
	UPDATE_REVIEW(OK,"리뷰 수정 완료"),
	DELETE_REVIEW(OK,"리뷰 삭제 완료"),
	SUCCESS_REVIEW(OK,"리뷰 조회 완료"),

	UPDATE_MONEY(OK,"사용자 머니 수정 완료"),
	SUCCESS_USER(OK,"사용자 조회 완료"),
	UPDATE_SUCCESS_PASSWORD(OK,"사용자 비밀번호 수정 완료"),

	CREATE_RESTAURANT(OK,"식당 생성완료"),
	SUCCESS_RESTAURANT(OK,"식당 조회완료"),
	UPDATE_RESTAURANT(OK,"식당 수정 완료"),
	DELETE_RESTAURANT(OK,"식당 삭제 완료"),
	ADMIN_SUCCESS_RESTAURANT(OK,"관리자 식당 정보 조회 완료"),

	SUCCESS_LIKES_RESTAURANT(OK, "좋아요 설정 완료"),
	DELETE_LIKES_RESTAURANT(OK, "좋아요 취소 완료"),
	SUCCESS_LIKES_RESTAURANT_LIST(OK, "사용자가 좋아요한 가게 리스트 조회 완료"),
	SUCCESS_LIKES_USERS_LIST(OK,"가게를 좋아요 등록한 사용자 리스트 조회 완료");


	private final HttpStatus httpStatus;
	private final String message;
}
