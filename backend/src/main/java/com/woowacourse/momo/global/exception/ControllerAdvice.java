package com.woowacourse.momo.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.woowacourse.momo.global.exception.dto.response.ExceptionResponse;
import com.woowacourse.momo.global.exception.exception.ErrorCode;
import com.woowacourse.momo.global.exception.exception.MomoException;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(MomoException.class)
    public ResponseEntity<ExceptionResponse> handleException(MomoException e) {
        return ResponseEntity.status(e.getStatusCode()).body(ExceptionResponse.from(e.getErrorCode()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().body(ExceptionResponse.from(ErrorCode.VALIDATION_ERROR.getErrorCode()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> handleAnyException(Exception e) {
        return ResponseEntity.internalServerError().body(ExceptionResponse.from(ErrorCode.INTERNAL_SERVER_ERROR.getErrorCode()));
    }
}
