package com.woowacourse.momo.group.service.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import com.woowacourse.momo.group.domain.duration.Duration;
import com.woowacourse.momo.group.domain.group.Group;
import com.woowacourse.momo.group.domain.schedule.Schedule;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.dto.response.MemberResponseAssembler;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GroupResponseAssembler {

    public static GroupResponse groupResponse(Group group, Member member) {
        return new GroupResponse(group.getName(), MemberResponseAssembler.memberResponse(member),
                group.getCategory().getId(), durationResponse(group.getDuration()),
                scheduleResponses(group.getSchedules()), group.getDeadline(), group.getLocation(),
                group.getDescription());
    }


    public static GroupPageResponse groupPageResponse(List<GroupSummaryResponse> groupSummaryResponses, boolean hasNextPage) {
        return new GroupPageResponse(hasNextPage, groupSummaryResponses);
    }

    public static GroupSummaryResponse groupSummaryResponse(Group group, Member member) {
        return new GroupSummaryResponse(group.getId(), group.getName(), MemberResponseAssembler.memberResponse(member),
                group.getCategory().getId(), group.getDeadline());
    }

    public static GroupIdResponse groupIdResponse(Group group) {
        return new GroupIdResponse(group.getId());
    }

    private static DurationResponse durationResponse(Duration duration) {
        return new DurationResponse(duration.getStartDate(), duration.getEndDate());
    }

    private static List<ScheduleResponse> scheduleResponses(List<Schedule> schedules) {
        return schedules.stream()
                .map(GroupResponseAssembler::scheduleResponse)
                .collect(Collectors.toList());
    }

    private static ScheduleResponse scheduleResponse(Schedule schedule) {
        return new ScheduleResponse(schedule.getDate(), schedule.getStartTime(), schedule.getEndTime());
    }
}
