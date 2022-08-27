package com.woowacourse.momo.fixture.calendar;

import static com.woowacourse.momo.fixture.calendar.datetime.DateTimeFixture.내일_23시_59분;
import static com.woowacourse.momo.fixture.calendar.datetime.DateTimeFixture.이틀후_23시_59분;
import static com.woowacourse.momo.fixture.calendar.datetime.DateTimeFixture.일주일후_23시_59분;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

import com.woowacourse.momo.fixture.calendar.datetime.DateTimeFixture;
import com.woowacourse.momo.group.domain.calendar.Deadline;

@SuppressWarnings("NonAsciiCharacters")
public enum DeadlineFixture {

    내일_23시_59분까지(내일_23시_59분),
    이틀후_23시_59분까지(이틀후_23시_59분),
    일주일후_23시_59분까지(일주일후_23시_59분),
    ;

    private final Deadline instance;

    DeadlineFixture(DateTimeFixture dateTime) {
        this.instance = new Deadline(dateTime.getDateTime());
    }

    public Deadline getDeadline() {
        return instance;
    }
}
