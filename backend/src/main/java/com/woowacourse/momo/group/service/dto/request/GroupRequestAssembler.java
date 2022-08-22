package com.woowacourse.momo.group.service.dto.request;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import com.woowacourse.momo.group.domain.calendar.Deadline;
import com.woowacourse.momo.group.domain.calendar.Duration;
import com.woowacourse.momo.group.domain.calendar.Schedule;
import com.woowacourse.momo.group.domain.group.Capacity;
import com.woowacourse.momo.group.domain.group.Group;
import com.woowacourse.momo.group.domain.group.GroupName;
import com.woowacourse.momo.member.domain.Member;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GroupRequestAssembler {

    public static Group group(Member host, GroupRequest request) {
        return new Group.Builder()
                .name(groupName(request))
                .host(host)
                .categoryId(request.getCategoryId())
                .capacity(capacity(request))
                .duration(duration(request.getDuration()))
                .deadline(deadline(request))
                .schedules(schedules(request.getSchedules()))
                .location(request.getLocation())
                .description(request.getDescription())
                .build();
    }

    public static GroupName groupName(GroupRequest request) {
        return new GroupName(request.getName());
    }

    public static GroupName groupName(GroupUpdateRequest request) {
        return new GroupName(request.getName());
    }

    public static Capacity capacity(GroupRequest request) {
        return new Capacity(request.getCapacity());
    }

    public static Capacity capacity(GroupUpdateRequest request) {
        return new Capacity(request.getCapacity());
    }

    public static Duration duration(DurationRequest request) {
        return new Duration(request.getStart(), request.getEnd());
    }

    public static Deadline deadline(GroupRequest request) {
        return new Deadline(request.getDeadline());
    }

    public static Deadline deadline(GroupUpdateRequest request) {
        return new Deadline(request.getDeadline());
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
