package com.woowacourse.momo.group.service.dto.request;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GroupRequest {

    private String name;
    private Long hostId;
    private Long categoryId;
    private Boolean regular;
    private DurationRequest duration;
    private List<ScheduleRequest> schedules;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime deadline;
    private String location;
    private String description;
}
