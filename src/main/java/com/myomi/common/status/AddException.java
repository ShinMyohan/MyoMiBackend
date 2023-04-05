package com.myomi.common.status;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@Getter
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AddException extends RuntimeException {
	private ErrorCode errorCode;
    private String detail;
    
    public AddException(ErrorCode errorCode, String detail) {
        this.errorCode = errorCode;
        this.detail = detail;
    }
}
