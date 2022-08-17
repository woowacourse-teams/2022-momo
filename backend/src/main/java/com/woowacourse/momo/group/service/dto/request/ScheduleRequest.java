package com.woowacourse.momo.group.service.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ScheduleRequest {

    @NotNull
    @DateTimeFormat
    private LocalDate date;
    @NotNull
    @DateTimeFormat
    private LocalTime startTime;
    @NotNull
    @DateTimeFormat
    private LocalTime endTime;
}
