package com.woowacourse.momo.globalException.dto.response;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExceptionResponse {

    private final String message;

    public static ExceptionResponse from(Exception e) {
        return new ExceptionResponse(e.getMessage());
    }

    public static ExceptionResponse from(MethodArgumentNotValidException e) {
        StringBuilder message = new StringBuilder();

        for (FieldError error : e.getFieldErrors()) {
            message.append(error.getDefaultMessage()).append(" ");
        }
        return new ExceptionResponse(new String(message));
    }
}
