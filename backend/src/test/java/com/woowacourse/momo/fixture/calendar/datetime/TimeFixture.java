package com.woowacourse.momo.fixture.calendar.datetime;

import java.time.LocalTime;

@SuppressWarnings("NonAsciiCharacters")
public enum TimeFixture {

    _10시_00분(10, 0),
    _12시_00분(12, 0),
    _23시_59분(23, 59),
    ;

    private final LocalTime instance;

    TimeFixture(int hour, int minute) {
        this.instance = LocalTime.of(hour, minute);
    }

    public LocalTime toTime() {
        return instance;
    }
}
