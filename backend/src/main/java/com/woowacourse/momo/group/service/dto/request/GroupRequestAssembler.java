package com.woowacourse.momo.group.service.dto.request;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.group.domain.Group;
import com.woowacourse.momo.group.domain.GroupName;
import com.woowacourse.momo.group.domain.calendar.Calendar;
import com.woowacourse.momo.group.domain.calendar.Deadline;
import com.woowacourse.momo.group.domain.calendar.Duration;
import com.woowacourse.momo.group.domain.calendar.Schedule;
import com.woowacourse.momo.group.domain.calendar.Schedules;
import com.woowacourse.momo.group.domain.participant.Capacity;
import com.woowacourse.momo.member.domain.Member;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GroupRequestAssembler {

    public static Group group(Member host, GroupRequest request) {
        return new Group(groupName(request), host, Category.from(request.getCategoryId()), capacity(request),
                duration(request.getDuration()), deadline(request), schedules(request.getSchedules()),
                request.getLocation(), request.getDescription());
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

    public static Calendar calendar(GroupUpdateRequest request) {
        return new Calendar(deadline(request), duration(request.getDuration()), schedules(request.getSchedules()));
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

    public static Schedules schedules(List<ScheduleRequest> requests) {
        return new Schedules(
                requests.stream()
                        .map(GroupRequestAssembler::schedule)
                        .collect(Collectors.toList())
        );
    }

    private static Schedule schedule(ScheduleRequest request) {
        return new Schedule(request.getDate(), request.getStartTime(), request.getEndTime());
    }
}
