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
	DELETE_MENU(OK,"메뉴 삭제 완료");


	private final HttpStatus httpStatus;
	private final String message;
}