package com.woowacourse.momo.service.dto.response;

import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TimeResponse {

    @DateTimeFormat
    private LocalTime start;
    @DateTimeFormat
    private LocalTime end;
}
