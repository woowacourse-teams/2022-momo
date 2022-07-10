package com.woowacourse.momo.service.dto.request;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DurationRequest {

    @DateTimeFormat
    private LocalDate start;
    @DateTimeFormat
    private LocalDate end;
}
