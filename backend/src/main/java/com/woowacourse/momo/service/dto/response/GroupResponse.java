package com.woowacourse.momo.service.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.woowacourse.momo.domain.group.Group;
import com.woowacourse.momo.domain.group.Schedules;
import com.woowacourse.momo.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupResponse {

    private String name;
    private MemberResponse host;
    private Long categoryId;
    private Boolean regular;
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
                .regular(group.isRegular())
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
