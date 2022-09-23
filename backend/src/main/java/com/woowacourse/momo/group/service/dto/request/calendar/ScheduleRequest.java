package com.woowacourse.momo.group.service.dto.request.calendar;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.group.domain.calendar.Schedule;

@RequiredArgsConstructor
public class ScheduleRequest {

    private final LocalDate date;
    private final LocalTime startTime;
    private final LocalTime endTime;

    public Schedule getSchedule() {
        return new Schedule(date, startTime, endTime);
    }
}
