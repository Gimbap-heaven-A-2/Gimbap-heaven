package com.sparta.gimbap_heaven.exception;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.Generated;

@Data
@Generated
public class ErrorResponse {
    private HttpStatus status;
    private String message;

    public ErrorResponse(ErrorCode errorCode){
        this.status = errorCode.getHttpStatus();
        this.message = errorCode.getMessage();
    }

    public ErrorResponse(HttpStatus httpStatus, String message) {
        this.status = httpStatus;
        this.message = message;
    }


}
