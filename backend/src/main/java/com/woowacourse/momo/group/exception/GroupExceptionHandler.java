package com.woowacourse.momo.group.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.woowacourse.momo.global.exception.dto.response.ExceptionResponse;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class GroupExceptionHandler {

    @ExceptionHandler(GroupException.class)
    public ResponseEntity<ExceptionResponse> handleException(GroupException e) {
        GroupExceptionCode exceptionCode = GroupExceptionCode.from(e);
        return ResponseEntity.status(exceptionCode.getStatus())
                .body(ExceptionResponse.from(exceptionCode.getMessage()));
    }
}
