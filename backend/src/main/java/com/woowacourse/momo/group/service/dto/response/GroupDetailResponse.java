package com.woowacourse.momo.group.service.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.woowacourse.momo.member.dto.response.MemberResponse;

@Getter
@AllArgsConstructor
public class GroupDetailResponse {

    private String name;
    private MemberResponse host;
    private Long categoryId;
    private DurationResponse duration;
    private List<ScheduleResponse> schedules;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime deadline;
    private String location;
    private String description;
}
