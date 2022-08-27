package com.woowacourse.momo.fixture.calendar.datetime;

import static com.woowacourse.momo.fixture.calendar.datetime.DateFixture.내일;
import static com.woowacourse.momo.fixture.calendar.datetime.DateFixture.어제;
import static com.woowacourse.momo.fixture.calendar.datetime.DateFixture.이틀후;
import static com.woowacourse.momo.fixture.calendar.datetime.DateFixture.일주일후;
import static com.woowacourse.momo.fixture.calendar.datetime.TimeFixture._23시_59분;

import java.time.LocalDateTime;

@SuppressWarnings("NonAsciiCharacters")
public enum DateTimeFixture {

    어제_23시_59분(어제, _23시_59분),
    내일_23시_59분(내일, _23시_59분),
    이틀후_23시_59분(이틀후, _23시_59분),
    일주일후_23시_59분(일주일후, _23시_59분),
    ;

    private final LocalDateTime instance;

    DateTimeFixture(DateFixture date, TimeFixture time) {
        this.instance = LocalDateTime.of(date.toDate(), time.toTime());
    }

    public LocalDateTime toDateTime() {
        return instance;
    }
}
