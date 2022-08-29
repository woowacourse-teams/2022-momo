package com.woowacourse.momo.fixture.calendar;

import static com.woowacourse.momo.fixture.calendar.datetime.DateFixture.이틀후;
import static com.woowacourse.momo.fixture.calendar.datetime.DateFixture.일주일후;

import com.woowacourse.momo.fixture.calendar.datetime.DateFixture;
import com.woowacourse.momo.group.domain.calendar.Duration;

@SuppressWarnings("NonAsciiCharacters")
public enum DurationFixture {

    이틀후_하루동안(이틀후, 이틀후),
    이틀후부터_일주일후까지(이틀후, 일주일후),
    일주일후_하루동안(일주일후, 일주일후),
    ;

    private final Duration instance;

    DurationFixture(DateFixture start, DateFixture end) {
        this.instance = new Duration(start.getDate(), end.getDate());
    }

    public Duration getDuration() {
        return instance;
    }
}
