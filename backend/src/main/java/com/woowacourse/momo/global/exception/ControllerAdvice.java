package com.woowacourse.momo.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.woowacourse.momo.global.exception.dto.response.ExceptionResponse;
import com.woowacourse.momo.global.exception.exception.ErrorCode;
import com.woowacourse.momo.global.exception.exception.GlobalErrorCode;
import com.woowacourse.momo.global.exception.exception.MomoException;
import com.woowacourse.momo.support.logging.UnhandledErrorLogging;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(MomoException.class)
    public ResponseEntity<ExceptionResponse> handleException(MomoException e) {
        int statusCode = e.getStatusCode();
        ExceptionResponse response = ExceptionResponse.from(e.getErrorCode());

        return ResponseEntity.status(statusCode).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException() {
        return convert(GlobalErrorCode.VALIDATION_ERROR);
    }

    @ExceptionHandler({NoHandlerFoundException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ExceptionResponse> notSupportedUriException() {
        return convert(GlobalErrorCode.NOT_SUPPORTED_URI_ERROR);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ExceptionResponse> notSupportedMethodException() {
        return convert(GlobalErrorCode.NOT_SUPPORTED_METHOD_ERROR);
    }

    @UnhandledErrorLogging
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> handleAnyException() {
        return convert(GlobalErrorCode.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ExceptionResponse> convert(ErrorCode errorCode) {
        int statusCode = errorCode.getStatusCode();
        ExceptionResponse response = ExceptionResponse.from(errorCode.getErrorCode());

        return ResponseEntity.status(statusCode).body(response);
    }
}
