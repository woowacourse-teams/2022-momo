package com.woowacourse.momo.group.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.woowacourse.momo.group.domain.schedule.Schedule;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleRequest {

    private String day;
    private TimeRequest time;

    public Schedule toEntity() {
        return Schedule.of(day, time.getStart(), time.getEnd());
    }
}
