package com.woowacourse.momo.fixture.calendar.datetime;

import java.time.LocalDate;

@SuppressWarnings("NonAsciiCharacters")
public enum DateFixture {

    어제(-1),
    내일(1),
    이틀후(2),
    일주일후(7),
    ;

    private final LocalDate instance;

    DateFixture(int days) {
        this.instance = LocalDate.now().plusDays(days);
    }

    public LocalDate getDate() {
        return instance;
    }
}
