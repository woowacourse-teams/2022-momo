package com.woowacourse.momo.group.service.dto.request;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import com.woowacourse.momo.group.domain.duration.Duration;
import com.woowacourse.momo.group.domain.group.Group;
import com.woowacourse.momo.group.domain.schedule.Schedule;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GroupRequestAssembler {

    public static Group group(GroupRequest request) {
        return new Group(request.getName(), request.getHostId(), request.getCategoryId(),
                duration(request.getDuration()), request.getDeadline(), schedules(request.getSchedules()),
                request.getLocation(), request.getDescription());
    }

    public static Duration duration(DurationRequest request) {
        return new Duration(request.getStart(), request.getEnd());
    }

    public static List<Schedule> schedules(List<ScheduleRequest> requests) {
        return requests.stream()
                .map(GroupRequestAssembler::schedule)
                .collect(Collectors.toList());
    }

    private static Schedule schedule(ScheduleRequest request) {
        return new Schedule(request.getDate(), request.getStartTime(), request.getEndTime());
    }
}
