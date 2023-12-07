package com.sparta.gimbap_heaven.global.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.gimbap_heaven.global.constant.ResponseCode;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessResponse {
	private int status;

	private String message;

	private Object data;

	public SuccessResponse(ResponseCode successCode){
		this.status = successCode.getHttpStatus().value();
		this.message = successCode.getMessage();
	}
	public SuccessResponse(ResponseCode successCode, Object data){
		this.status = successCode.getHttpStatus().value();
		this.message = successCode.getMessage();
		this.data = data;
	}
	public SuccessResponse(int status, String message ) {
		this.status = status;
		this.message = message;
	}

}
