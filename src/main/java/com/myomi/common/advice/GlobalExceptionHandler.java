package com.myomi.common.advice;

import com.myomi.common.status.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
    
    @ExceptionHandler(PointValidFailedException.class)
    public ResponseEntity<ErrorResponse> pointValidFailedException(PointValidFailedException ex) {
        log.error("PointValidFailedException", ex);
        ErrorResponse response = new ErrorResponse(ex.getErrorCode(), ex.getDetail());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
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
