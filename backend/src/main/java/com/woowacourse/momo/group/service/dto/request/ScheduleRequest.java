package com.woowacourse.momo.group.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.woowacourse.momo.group.domain.Schedule;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleRequest {

    private String day;
    @DateTimeFormat
    private LocalTime startTime;
    @DateTimeFormat
    private LocalTime endTime;

    public Schedule toEntity() {
        return Schedule.of(day, startTime, endTime);
    }
}
