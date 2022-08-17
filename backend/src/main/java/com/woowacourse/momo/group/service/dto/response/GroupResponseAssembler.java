package com.woowacourse.momo.group.service.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import com.woowacourse.momo.group.domain.group.Group;
import com.woowacourse.momo.group.domain.schedule.Duration;
import com.woowacourse.momo.group.domain.schedule.Schedule;
import com.woowacourse.momo.member.service.dto.response.MemberResponseAssembler;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GroupResponseAssembler {

    public static GroupResponse groupResponse(Group group) {
        return new GroupResponse(group.getName(), MemberResponseAssembler.memberResponse(group.getHost()),
                group.getCategory().getId(), group.getCapacity(), durationResponse(group.getDuration()),
                scheduleResponses(group.getSchedules()), group.isFinishedRecruitment(), group.getDeadline(),
                group.getLocation(), group.getDescription());
    }

    public static List<GroupSummaryResponse> groupSummaryResponses(List<Group> groups) {
        return groups.stream()
                .map(GroupResponseAssembler::groupSummaryResponse)
                .collect(Collectors.toList());
    }

    public static GroupSummaryResponse groupSummaryResponse(Group group) {
        return new GroupSummaryResponse(group.getId(), group.getName(),
                MemberResponseAssembler.memberResponse(group.getHost()), group.getCategory().getId(),
                group.getCapacity(), group.getParticipants().size(), group.isFinishedRecruitment(),
                group.getDeadline());
    }

    public static GroupPageResponse groupPageResponse(List<GroupSummaryResponse> groupSummaryResponses,
                                                      boolean hasNextPage, int pageNumber) {
        return new GroupPageResponse(hasNextPage, pageNumber, groupSummaryResponses);
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
