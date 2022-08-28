package com.woowacourse.momo.group.controller.param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import com.woowacourse.momo.group.controller.param.calendar.DurationParam;
import com.woowacourse.momo.group.controller.param.calendar.ScheduleParam;
import com.woowacourse.momo.group.service.request.GroupRequest;
import com.woowacourse.momo.group.service.request.calendar.DeadlineRequest;
import com.woowacourse.momo.group.service.request.calendar.DurationRequest;
import com.woowacourse.momo.group.service.request.calendar.ScheduleRequest;
import com.woowacourse.momo.group.service.request.calendar.SchedulesRequest;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Component
public class GroupRequestAssembler {

    public GroupRequest groupRequest(GroupParam param) {
        return new GroupRequest(param.getName(), param.getCategoryId(), param.getCapacity(),
                durationRequest(param.getDuration()), schedulesRequest(param.getSchedules()),
                deadlineRequest(param.getDeadline()), param.getLocation(), param.getDescription());
    }

    private DurationRequest durationRequest(DurationParam param) {
        return new DurationRequest(param.getStart(), param.getEnd());
    }

    private SchedulesRequest schedulesRequest(List<ScheduleParam> params) {
        return new SchedulesRequest(params.stream()
                .map(this::scheduleRequest)
                .collect(Collectors.toUnmodifiableList()));
    }

    private ScheduleRequest scheduleRequest(ScheduleParam param) {
        return new ScheduleRequest(param.getDate(), param.getStartTime(), param.getEndTime());
    }

    private DeadlineRequest deadlineRequest(LocalDateTime deadline) {
        return new DeadlineRequest(deadline);
    }
}
