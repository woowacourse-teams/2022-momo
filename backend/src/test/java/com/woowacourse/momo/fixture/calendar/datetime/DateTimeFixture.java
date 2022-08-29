package com.woowacourse.momo.fixture.calendar.datetime;

import static com.woowacourse.momo.fixture.calendar.datetime.DateFixture.내일;
import static com.woowacourse.momo.fixture.calendar.datetime.DateFixture.어제;
import static com.woowacourse.momo.fixture.calendar.datetime.DateFixture.이틀후;
import static com.woowacourse.momo.fixture.calendar.datetime.DateFixture.일주일후;
import static com.woowacourse.momo.fixture.calendar.datetime.TimeFixture._23시_59분;

import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;

@SuppressWarnings("NonAsciiCharacters")
@RequiredArgsConstructor
public enum DateTimeFixture {


    어제_23시_59분(어제, _23시_59분),
    내일_23시_59분(내일, _23시_59분),
    이틀후_23시_59분(이틀후, _23시_59분),
    일주일후_23시_59분(일주일후, _23시_59분);

    private final DateFixture date;
    private final TimeFixture time;

    private LocalDateTime instance;

    public LocalDateTime getInstance() {
        if (instance == null) {
            instance = LocalDateTime.of(date.getInstance(), time.getInstance());
        }
        return instance;
    }
}
