package com.sparta.gimbap_heaven.global.dto;

import org.springframework.http.HttpStatus;

import com.sparta.gimbap_heaven.global.constant.ErrorCode;
import com.sparta.gimbap_heaven.global.constant.ResponseCode;

import lombok.Data;

@Data
public class SuccessResponse {
	private HttpStatus httpStatus;
	private int status;
	private String message;
	public SuccessResponse(ResponseCode successCode){
		this.status = successCode.getHttpStatus().value();
		this.message = successCode.getMessage();
	}
	public SuccessResponse(int status, String message) {
		this.status = status;
		this.message = message;
	}
	public SuccessResponse(HttpStatus httpStatus, String message) {
		this.httpStatus = httpStatus;
		this.message = message;
	}
}
