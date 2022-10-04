package com.woowacourse.momo.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.woowacourse.momo.global.exception.dto.response.ExceptionResponse;
import com.woowacourse.momo.global.exception.exception.ErrorCode;
import com.woowacourse.momo.global.exception.exception.GlobalErrorCode;
import com.woowacourse.momo.global.exception.exception.MomoException;
import com.woowacourse.momo.global.logging.UnhandledErrorLogging;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(MomoException.class)
    public ResponseEntity<ExceptionResponse> handleException(MomoException e) {
        return ResponseEntity.status(e.getStatusCode()).body(ExceptionResponse.from(e.getErrorCode()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest()
                .body(ExceptionResponse.from(GlobalErrorCode.VALIDATION_ERROR.getErrorCode()));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ExceptionResponse> unhandledApiException(NoHandlerFoundException exception) {
        ErrorCode e = GlobalErrorCode.UNHANDLED_API_ERROR;
        return ResponseEntity.status(e.getStatusCode()).body(ExceptionResponse.from(e.getErrorCode()));
    }

    @UnhandledErrorLogging
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> handleAnyException(Exception e) {
        return ResponseEntity.internalServerError()
                .body(ExceptionResponse.from(GlobalErrorCode.INTERNAL_SERVER_ERROR.getErrorCode()));
    }
}
