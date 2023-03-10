package com.myomi.common.status;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class ProductSoldOutException extends RuntimeException {
    private ErrorCode errorCode;
    private String detail;

    public ProductSoldOutException(ErrorCode errorCode, String detail) {
        this.errorCode = errorCode;
        this.detail = detail;
    }
}
