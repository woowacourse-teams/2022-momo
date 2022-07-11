package com.woowacourse.momo.group.service.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.woowacourse.momo.group.domain.Group;
import com.woowacourse.momo.group.domain.Schedules;
import com.woowacourse.momo.member.domain.Member;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupResponse {

    private String name;
    private MemberResponse host;
    private Long categoryId;
    private DurationResponse duration;
    private List<ScheduleResponse> schedules;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime deadline;
    private String location;
    private String description;

    public static GroupResponse toResponse(Group group, Member host) {
        return GroupResponse.builder()
                .name(group.getName())
                .host(MemberResponse.toResponse(host))
                .categoryId(group.getCategoryId())
                .duration(DurationResponse.toResponse(group.getDuration()))
                .schedules(convertSchedulesToResponse(group.getSchedules()))
                .deadline(group.getDeadline())
                .location(group.getLocation())
                .description(group.getDescription())
                .build();
    }

    private static List<ScheduleResponse> convertSchedulesToResponse(Schedules schedules) {
        return schedules.getValue()
                .stream()
                .map(ScheduleResponse::toResponse)
                .collect(Collectors.toList());
    }
}
