package com.woowacourse.momo.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TimeRequest {

    @DateTimeFormat
    private LocalTime start;
    @DateTimeFormat
    private LocalTime end;
}
