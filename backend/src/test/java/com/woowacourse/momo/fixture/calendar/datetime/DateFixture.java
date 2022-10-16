package com.woowacourse.momo.fixture.calendar.datetime;

import java.time.LocalDate;

@SuppressWarnings("NonAsciiCharacters")
public enum DateFixture {

    어제(-1),
    오늘(0),
    내일(1),
    이틀후(2),
    삼일후(3),
    일주일후(7),
    ;

    private final LocalDate instance;

    DateFixture(int days) {
        this.instance = newDate(days);
    }

    public LocalDate toDate() {
        return instance;
    }

    public static LocalDate newDate(int days) {
        return LocalDate.now().plusDays(days);
    }
}
