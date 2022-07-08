package com.woowacourse.momo.service.dto.response;

import com.woowacourse.momo.domain.group.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleResponse {

    private String day;
    private TimeResponse time;

    public static ScheduleResponse toResponse(Schedule schedule) {
        return new ScheduleResponse(schedule.getReservationDay().getValue(),
                new TimeResponse(schedule.getStartTime(), schedule.getEndTime()));
    }
}
