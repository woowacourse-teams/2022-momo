package com.woowacourse.momo.service.dto.response.group;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DurationResponse {

    @DateTimeFormat
    private LocalDate start;
    @DateTimeFormat
    private LocalDate end;
}
