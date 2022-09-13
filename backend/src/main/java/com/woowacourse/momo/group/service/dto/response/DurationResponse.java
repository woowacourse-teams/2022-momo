package com.woowacourse.momo.group.service.dto.response;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DurationResponse {

    @DateTimeFormat
    private LocalDate start;

    @DateTimeFormat
    private LocalDate end;
}
