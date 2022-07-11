package com.woowacourse.momo.group.service.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.woowacourse.momo.group.domain.schedule.Schedule;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleResponse {

    @DateTimeFormat
    private LocalDate date;
    @DateTimeFormat
    private LocalTime startTime;
    @DateTimeFormat
    private LocalTime endTime;

    public static ScheduleResponse toResponse(Schedule schedule) {
        return new ScheduleResponse(schedule.getDate(), schedule.getStartTime(), schedule.getEndTime());
    }
}
