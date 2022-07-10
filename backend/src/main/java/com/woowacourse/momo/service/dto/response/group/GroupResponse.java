package com.woowacourse.momo.service.dto.response.group;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.woowacourse.momo.service.dto.response.member.MemberResponse;

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
}
