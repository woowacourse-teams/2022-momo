package com.woowacourse.momo.fixture.calendar;

import static com.woowacourse.momo.fixture.calendar.datetime.DateFixture.이틀후;
import static com.woowacourse.momo.fixture.calendar.datetime.DateFixture.일주일후;
import static com.woowacourse.momo.fixture.calendar.datetime.TimeFixture._10시_00분;
import static com.woowacourse.momo.fixture.calendar.datetime.TimeFixture._12시_00분;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.fixture.calendar.datetime.DateFixture;
import com.woowacourse.momo.fixture.calendar.datetime.TimeFixture;
import com.woowacourse.momo.group.domain.calendar.Schedule;

@SuppressWarnings("NonAsciiCharacters")
@RequiredArgsConstructor
public enum ScheduleFixture {

    이틀후_10시부터_12시까지(이틀후, _10시_00분, _12시_00분),
    일주일후_10시부터_12시까지(일주일후, _10시_00분, _12시_00분);

    private final DateFixture date;
    private final TimeFixture startTime;
    private final TimeFixture endTime;

    public Schedule newInstance() {
        return new Schedule(date.getDate(), startTime.getTime(), endTime.getTime());
    }
}
