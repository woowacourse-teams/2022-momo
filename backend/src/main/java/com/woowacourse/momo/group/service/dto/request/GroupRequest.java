package com.woowacourse.momo.group.service.dto.request;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class GroupRequest {

    @NotNull
    private String name;
    @NotNull
    private Long categoryId;
    @NotNull
    private Integer capacity;
    @NotNull
    private DurationRequest duration;
    @NotNull
    private List<ScheduleRequest> schedules;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime deadline;
    @NotNull
    private String location;
    @NotNull
    private String description;
}
