package com.woowacourse.momo.domain;

import java.time.LocalDateTime;
import java.util.List;

public class Group {

    private final long id;
    private final Member host;
    private final Category category;
    private final boolean regular;
    private final Duration duration;
    private final LocalDateTime deadline;
    private final List<Schedule> schedules;
    private final String location;
    private final String description;

    public Group(final long id, final Member host, final Category category, final boolean regular,
                 final Duration duration, final LocalDateTime deadline, final List<Schedule> schedules,
                 final String location, final String description) {
        this.id = id;
        this.host = host;
        this.category = category;
        this.regular = regular;
        this.duration = duration;
        this.deadline = deadline;
        this.schedules = schedules;
        this.location = location;
        this.description = description;
    }
}
