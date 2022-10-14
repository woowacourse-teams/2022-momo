package com.woowacourse.momo.group.service.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import com.woowacourse.momo.favorite.domain.Favorite;
import com.woowacourse.momo.group.domain.Group;
import com.woowacourse.momo.group.domain.Location;
import com.woowacourse.momo.group.domain.calendar.Duration;
import com.woowacourse.momo.group.domain.calendar.Schedule;
import com.woowacourse.momo.group.domain.search.dto.GroupSummaryRepositoryResponse;
import com.woowacourse.momo.member.service.dto.response.MemberResponse;
import com.woowacourse.momo.member.service.dto.response.MemberResponseAssembler;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GroupResponseAssembler {

    public static GroupResponse groupResponse(Group group, String imageUrl, boolean isMemberLiked) {
        return new GroupResponse(group.getName(), MemberResponseAssembler.memberResponse(group.getHost()),
                group.getCategory().getId(), group.getCapacity(), durationResponse(group.getDuration()),
                scheduleResponses(group.getSchedules()), group.isFinishedRecruitment(), group.getDeadline(),
                locationResponse(group.getLocation()), isMemberLiked, group.getDescription().getValue(),
                imageUrl);
    }

    public static GroupResponse groupResponse(Group group, String imageUrl) {
        return groupResponse(group, imageUrl, false);
    }

    public static List<GroupSummaryResponse> groupSummaryResponses(List<GroupSummaryRepositoryResponse> responses,
                                                                   List<Favorite> favorites) {
        return responses.stream()
                .map(response -> GroupResponseAssembler.groupSummaryResponse(response, favorites))
                .collect(Collectors.toList());
    }

    private static GroupSummaryResponse groupSummaryResponse(GroupSummaryRepositoryResponse response,
                                                             List<Favorite> favorites) {
        boolean isFavorite = anyFavoriteMatches(response, favorites);
        return groupSummaryResponse(response, isFavorite);
    }

    private static boolean anyFavoriteMatches(GroupSummaryRepositoryResponse response,
                                              List<Favorite> favorites) {
        return favorites.stream()
                .anyMatch(favorite -> favorite.isSameGroup(response.getGroupId()));
    }

    public static List<GroupSummaryResponse> groupSummaryResponses(List<GroupSummaryRepositoryResponse> responses) {
        return responses.stream()
                .map(GroupResponseAssembler::groupSummaryResponse)
                .collect(Collectors.toList());
    }

    private static GroupSummaryResponse groupSummaryResponse(GroupSummaryRepositoryResponse response) {
        return groupSummaryResponse(response, false);
    }

    private static GroupSummaryResponse groupSummaryResponse(GroupSummaryRepositoryResponse response, boolean isFavorite) {
        return new GroupSummaryResponse(response.getGroupId(), response.getGroupName(),
                new MemberResponse(response.getHostId(), response.getHostName()), response.getCategory().getId(),
                response.getCapacity(), response.getNumOfParticipant(), isFinished(response),
                response.getDeadline(), isFavorite, response.getImageName());
    }

    private static boolean isFinished(GroupSummaryRepositoryResponse response) {
        return response.isClosedEarly() || response.getDeadline().isBefore(LocalDateTime.now());
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
