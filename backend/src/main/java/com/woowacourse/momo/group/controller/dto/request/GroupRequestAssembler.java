package com.woowacourse.momo.group.controller.dto.request;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.woowacourse.momo.group.controller.dto.request.calendar.DurationApiRequest;
import com.woowacourse.momo.group.controller.dto.request.calendar.ScheduleApiRequest;
import com.woowacourse.momo.group.service.dto.request.GroupRequest;
import com.woowacourse.momo.group.service.dto.request.LocationRequest;
import com.woowacourse.momo.group.service.dto.request.calendar.DeadlineRequest;
import com.woowacourse.momo.group.service.dto.request.calendar.DurationRequest;
import com.woowacourse.momo.group.service.dto.request.calendar.ScheduleRequest;
import com.woowacourse.momo.group.service.dto.request.calendar.SchedulesRequest;

@Component
public class GroupRequestAssembler {

    public GroupRequest groupRequest(GroupApiRequest request) {
        return new GroupRequest(request.getName(), request.getCategoryId(), request.getCapacity(),
                durationRequest(request.getDuration()), schedulesRequest(request.getSchedules()),
                deadlineRequest(request.getDeadline()), locationRequest(request.getLocation()),
                request.getDescription());
    }

    private DurationRequest durationRequest(DurationApiRequest request) {
        return new DurationRequest(request.getStart(), request.getEnd());
    }

    private SchedulesRequest schedulesRequest(List<ScheduleApiRequest> requests) {
        return new SchedulesRequest(requests.stream()
                .map(this::scheduleRequest)
                .collect(Collectors.toUnmodifiableList()));
    }

    private ScheduleRequest scheduleRequest(ScheduleApiRequest request) {
        return new ScheduleRequest(request.getDate(), request.getStartTime(), request.getEndTime());
    }

    private DeadlineRequest deadlineRequest(LocalDateTime deadline) {
        return new DeadlineRequest(deadline);
    }

    private LocationRequest locationRequest(LocationApiRequest request) {
        return new LocationRequest(request.getAddress(), request.getBuildingName(), request.getDetail());
    }
}
