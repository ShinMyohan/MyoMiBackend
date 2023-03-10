package com.myomi.common.status;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@Getter
@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class TokenValidFailedException extends RuntimeException {
    private ErrorCode errorCode;
    private String detail;

    public TokenValidFailedException(ErrorCode errorCode, String detail) {
        this.errorCode = errorCode;
        this.detail = detail;
    }
}
