package com.woowacourse.momo.service.dto.request;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.woowacourse.momo.domain.group.Group;
import com.woowacourse.momo.domain.group.Schedule;

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

    public Group toEntity() {
        return new Group(name, hostId, categoryId, regular, duration.toEntity(), deadline,
                convertSchedulesToEntity(), location, description);
    }

    private List<Schedule> convertSchedulesToEntity() {
        return schedules.stream()
                .map(ScheduleRequest::toEntity)
                .collect(Collectors.toList());
    }
}
