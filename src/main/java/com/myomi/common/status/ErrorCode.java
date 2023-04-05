package com.myomi.common.status;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    INTER_SERVER_ERROR(500, "INTER SERVER ERROR"),
    RESOURCE_NOT_FOUND(404, "RESOURCE NOT FOUND"),
    UNAUTHORIZED(401, "UNAUTHORIZED"),
    EXPIRED_JWT(401, "EXPIRED JWT"),
    BAD_REQUEST(400, "BAD REQUEST");

    private int status;
    private String message;
}
