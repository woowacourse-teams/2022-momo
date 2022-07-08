package com.woowacourse.momo.service.dto.request;

import com.woowacourse.momo.domain.group.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
