package com.woowacourse.momo.group.service.dto.request;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class DurationRequest {

    @DateTimeFormat
    private LocalDate start;
    @DateTimeFormat
    private LocalDate end;
}
