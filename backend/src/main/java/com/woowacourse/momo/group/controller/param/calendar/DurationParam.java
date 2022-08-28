package com.woowacourse.momo.group.controller.param.calendar;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class DurationParam {

    @NotNull
    @DateTimeFormat
    private LocalDate start;

    @NotNull
    @DateTimeFormat
    private LocalDate end;
}
