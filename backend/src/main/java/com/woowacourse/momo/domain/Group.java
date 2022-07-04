package com.woowacourse.momo.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Group {

    private final long id;
    private final Member host;
    private final Category category;
    private final boolean regular;
    private final LocalDate startDuration;
    private final LocalDate endDuration;
    private final LocalDateTime deadline;
    private final List<Schedule> schedules;
    private final String location;
    private final String description;

    public Group(final long id, final Member host, final Category category, final boolean regular,
                 final LocalDate startDuration, final LocalDate endDuration, final LocalDateTime deadline,
                 final List<Schedule> schedules, final String location, final String description) {
        this.id = id;
        this.host = host;
        this.category = category;
        this.regular = regular;
        this.startDuration = startDuration;
        this.endDuration = endDuration;
        this.deadline = deadline;
        this.schedules = schedules;
        this.location = location;
        this.description = description;
    }
}
