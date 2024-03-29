package com.myomi.common.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.myomi.common.status.ErrorCode;
import com.myomi.common.status.ErrorResponse;
import com.myomi.common.status.InternalServerException;
import com.myomi.common.status.NoResourceException;
import com.myomi.common.status.ProductSoldOutException;
import com.myomi.common.status.TokenValidFailedException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<ErrorResponse> internalServerException(InternalServerException ex) {
        log.error("InternalServerException", ex);
        ErrorResponse response = new ErrorResponse(ex.getErrorCode(), ex.getDetail());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        log.error("handleException", ex);
        ErrorResponse response = new ErrorResponse(ErrorCode.INTER_SERVER_ERROR, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(NoResourceException.class)
    public ResponseEntity<ErrorResponse> noResourceException(NoResourceException ex) {
        log.error("NoResourceException", ex);
        ErrorResponse response = new ErrorResponse(ex.getErrorCode(), ex.getDetail());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TokenValidFailedException.class)
    public ResponseEntity<ErrorResponse> accessDeniedException(TokenValidFailedException ex) {
        log.error("TokenValidFailedException", ex);
        ErrorResponse response = new ErrorResponse(ex.getErrorCode(), ex.getDetail());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ProductSoldOutException.class)
    public ResponseEntity<ErrorResponse> productSoldOutException(ProductSoldOutException ex) {
        log.error("ProductSoldOutException", ex);
        ErrorResponse response = new ErrorResponse(ex.getErrorCode(), ex.getDetail());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
