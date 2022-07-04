package com.woowacourse.momo.domain;

import java.time.LocalTime;

public class Schedule {

    private final long id;
    private final Day day;
    private final LocalTime startTime;
    private final LocalTime endTime;

    public Schedule(final long id, final Day day, final LocalTime startTime, final LocalTime endTime) {
        this.id = id;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
