package com.woowacourse.momo.group.service.dto.request.calendar;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.group.domain.calendar.Schedule;

@RequiredArgsConstructor
public class SchedulesRequest {

    private final List<ScheduleRequest> schedules;

    public List<Schedule> getSchedules() {
        return schedules.stream()
                .map(ScheduleRequest::getSchedule)
                .collect(Collectors.toUnmodifiableList());
    }
}
