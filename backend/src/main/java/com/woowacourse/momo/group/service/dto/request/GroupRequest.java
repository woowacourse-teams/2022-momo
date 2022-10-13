package com.woowacourse.momo.group.service.dto.request;

import java.util.List;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.group.domain.Description;
import com.woowacourse.momo.group.domain.GroupName;
import com.woowacourse.momo.group.domain.Location;
import com.woowacourse.momo.group.domain.calendar.Calendar;
import com.woowacourse.momo.group.domain.calendar.Duration;
import com.woowacourse.momo.group.domain.calendar.Schedule;
import com.woowacourse.momo.group.domain.participant.Capacity;
import com.woowacourse.momo.group.service.dto.request.calendar.DeadlineRequest;
import com.woowacourse.momo.group.service.dto.request.calendar.DurationRequest;
import com.woowacourse.momo.group.service.dto.request.calendar.SchedulesRequest;

@RequiredArgsConstructor
public class GroupRequest {

    private final String name;
    private final long categoryId;
    private final int capacity;
    private final DurationRequest duration;
    private final SchedulesRequest schedules;
    private final DeadlineRequest deadline;
    private final LocationRequest location;
    private final String description;

    public GroupName getName() {
        return new GroupName(name);
    }

    public Category getCategory() {
        return Category.from(categoryId);
    }

    public Capacity getCapacity() {
        return new Capacity(capacity);
    }

    public Calendar getCalendar() {
        return new Calendar(deadline.getDeadline(), duration.getDuration());
    }

    public Duration getDuration() {
        return duration.getDuration();
    }

    public List<Schedule> getSchedules() {
        return schedules.getSchedules();
    }

    public Location getLocation() {
        return location.getLocation();
    }

    public Description getDescription() {
        return new Description(description);
    }
}
