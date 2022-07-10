package com.woowacourse.momo.service.dto.request;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import com.woowacourse.momo.domain.group.Duration;
import com.woowacourse.momo.domain.group.Group;
import com.woowacourse.momo.domain.group.Schedule;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GroupRequestAssembler {

    public static Group group(GroupRequest request) {
        return new Group(request.getName(), request.getHostId(), request.getCategoryId(), request.getRegular(),
                duration(request.getDuration()), request.getDeadline(),
                schedules(request.getSchedules()), request.getLocation(), request.getDescription());
    }

    public static Duration duration(DurationRequest durationRequest) {
        return Duration.of(durationRequest.getStart(), durationRequest.getEnd());
    }

    private static List<Schedule> schedules(List<ScheduleRequest> scheduleRequests) {
        return scheduleRequests.stream()
                .map(GroupRequestAssembler::schedule)
                .collect(Collectors.toList());
    }

    private static Schedule schedule(ScheduleRequest scheduleRequest) {
        return Schedule.of(scheduleRequest.getDay(),
                scheduleRequest.getTime().getStart(), scheduleRequest.getTime().getEnd());
    }
}
