package com.woowacourse.momo.group.controller.dto.request;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.woowacourse.momo.group.controller.dto.request.calendar.DurationApiRequest;
import com.woowacourse.momo.group.controller.dto.request.calendar.ScheduleApiRequest;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class GroupApiRequest {

    @NotNull
    private String name;

    @NotNull
    private Long categoryId;

    @NotNull
    private Integer capacity;

    @NotNull
    private DurationApiRequest duration;

    @NotNull
    private List<ScheduleApiRequest> schedules;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime deadline;

    @NotNull
    private LocationApiRequest location;

    @NotNull
    private String description;
}
