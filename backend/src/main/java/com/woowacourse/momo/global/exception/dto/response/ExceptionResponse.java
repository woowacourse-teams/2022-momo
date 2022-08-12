package com.woowacourse.momo.global.exception.dto.response;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExceptionResponse {

    private final String message;

    public static ExceptionResponse from(String e) {
        return new ExceptionResponse(e);
    }

    public static ExceptionResponse from(MethodArgumentNotValidException e) {
        StringBuilder message = new StringBuilder();

        for (FieldError error : e.getFieldErrors()) {
            message.append(error.getDefaultMessage()).append(" ");
        }

        return new ExceptionResponse(new String(message));
    }

    public String getMessage() {
        return message;
    }
}
