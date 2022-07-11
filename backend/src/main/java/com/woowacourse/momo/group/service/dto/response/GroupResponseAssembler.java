package com.woowacourse.momo.group.service.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import com.woowacourse.momo.group.domain.duration.Duration;
import com.woowacourse.momo.group.domain.group.Group;
import com.woowacourse.momo.group.domain.schedule.Schedule;
import com.woowacourse.momo.group.domain.schedule.Schedules;
import com.woowacourse.momo.member.domain.member.Member;
import com.woowacourse.momo.member.service.dto.response.MemberResponseAssembler;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GroupResponseAssembler {

    public static GroupResponse groupResponse(Group group, Member host) {
        return GroupResponse.builder()
                .name(group.getName())
                .host(MemberResponseAssembler.memberResponse(host))
                .categoryId(group.getCategoryId())
                .regular(group.isRegular())
                .duration(durationResponse(group.getDuration()))
                .schedules(scheduleResponses(group.getSchedules()))
                .deadline(group.getDeadline())
                .location(group.getLocation())
                .description(group.getDescription())
                .build();
    }

    private static DurationResponse durationResponse(Duration duration) {
        return new DurationResponse(duration.getStartDate(), duration.getEndDate());
    }

    private static List<ScheduleResponse> scheduleResponses(Schedules schedules) {
        return schedules.getValue()
                .stream()
                .map(GroupResponseAssembler::scheduleResponse)
                .collect(Collectors.toList());
    }

    private static ScheduleResponse scheduleResponse(Schedule schedule) {
        return new ScheduleResponse(schedule.getReservationDay().getValue(), timeResponse(schedule));
    }

    private static TimeResponse timeResponse(Schedule schedule) {
        return new TimeResponse(schedule.getStartTime(), schedule.getEndTime());
    }
}
