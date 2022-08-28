package com.woowacourse.momo.group.controller.param;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.woowacourse.momo.group.controller.param.calendar.DurationParam;
import com.woowacourse.momo.group.controller.param.calendar.ScheduleParam;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class GroupParam {

    @NotNull
    private String name;

    @NotNull
    private Long categoryId;

    @NotNull
    private Integer capacity;

    @NotNull
    private DurationParam duration;

    @NotNull
    private List<ScheduleParam> schedules;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime deadline;

    @NotNull
    private String location;

    @NotNull
    private String description;
}
