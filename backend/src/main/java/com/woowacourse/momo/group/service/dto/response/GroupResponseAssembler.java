package com.woowacourse.momo.group.service.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import com.woowacourse.momo.favorite.domain.Favorite;
import com.woowacourse.momo.group.domain.Group;
import com.woowacourse.momo.group.domain.Location;
import com.woowacourse.momo.group.domain.calendar.Duration;
import com.woowacourse.momo.group.domain.calendar.Schedule;
import com.woowacourse.momo.member.service.dto.response.MemberResponseAssembler;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GroupResponseAssembler {

    public static GroupResponse groupResponseWithLogin(Group group, boolean isMemberLiked) {
        return new GroupResponse(group.getName(), MemberResponseAssembler.memberResponse(group.getHost()),
                group.getCategory().getId(), group.getCapacity(), durationResponse(group.getDuration()),
                scheduleResponses(group.getSchedules()), group.isFinishedRecruitment(), group.getDeadline(),
                locationResponse(group.getLocation()), isMemberLiked, group.getDescription().getValue());
    }

    public static GroupResponse groupResponseWithoutLogin(Group group) {
        return new GroupResponse(group.getName(), MemberResponseAssembler.memberResponse(group.getHost()),
                group.getCategory().getId(), group.getCapacity(), durationResponse(group.getDuration()),
                scheduleResponses(group.getSchedules()), group.isFinishedRecruitment(), group.getDeadline(),
                locationResponse(group.getLocation()), false, group.getDescription().getValue());
    }

    public static List<GroupSummaryResponse> groupSummaryResponsesWithLogin(List<Group> groups, List<Favorite> favorites) {
        return groups.stream()
                .map(group -> GroupResponseAssembler.groupSummaryResponseWithLogin(group, favorites))
                .collect(Collectors.toList());
    }

    public static List<GroupSummaryResponse> groupSummaryResponsesWithoutLogin(List<Group> groups) {
        return groups.stream()
                .map(GroupResponseAssembler::groupSummaryResponseWithoutLogin)
                .collect(Collectors.toList());
    }

    private static GroupSummaryResponse groupSummaryResponseWithLogin(Group group, List<Favorite> favorites) {
        boolean result = favorites.stream()
                .anyMatch(f -> f.isSameGroup(group));

        return new GroupSummaryResponse(group.getId(), group.getName(),
                MemberResponseAssembler.memberResponse(group.getHost()), group.getCategory().getId(),
                group.getCapacity(), group.getParticipants().size(), group.isFinishedRecruitment(),
                group.getDeadline(), result);
    }

    public static GroupSummaryResponse groupSummaryResponseWithoutLogin(Group group) {
        return new GroupSummaryResponse(group.getId(), group.getName(),
                MemberResponseAssembler.memberResponse(group.getHost()), group.getCategory().getId(),
                group.getCapacity(), group.getParticipants().size(), group.isFinishedRecruitment(),
                group.getDeadline(), false);
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

    private static LocationResponse locationResponse(Location location) {
        return new LocationResponse(location.getAddress(), location.getBuildingName(), location.getDetail());
    }
}
