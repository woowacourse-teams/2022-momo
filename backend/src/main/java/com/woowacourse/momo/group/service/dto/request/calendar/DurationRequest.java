package com.woowacourse.momo.group.service.dto.request.calendar;

import java.time.LocalDate;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.group.domain.calendar.Duration;

@RequiredArgsConstructor
public class DurationRequest {

    private final LocalDate start;
    private final LocalDate end;

    public Duration getDuration() {
        return new Duration(start, end);
    }
}
