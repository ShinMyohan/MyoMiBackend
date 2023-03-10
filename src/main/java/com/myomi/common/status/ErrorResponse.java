package com.myomi.common.status;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ErrorResponse {
    private Date timestamp;
    private int status;
    private String message;
    private String details;

    public ErrorResponse(ErrorCode errorCode, String details){
        this.timestamp = new Date();
        this.status = errorCode.getStatus();
        this.message = errorCode.getMessage();
        this.details = details;
    }
}
