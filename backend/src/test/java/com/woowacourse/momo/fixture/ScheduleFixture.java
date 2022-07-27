package com.woowacourse.momo.fixture;

import static com.woowacourse.momo.fixture.DateFixture._7월_1일;
import static com.woowacourse.momo.fixture.DateFixture._7월_2일;
import static com.woowacourse.momo.fixture.TimeFixture._10시_00분;
import static com.woowacourse.momo.fixture.TimeFixture._12시_00분;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.group.domain.schedule.Schedule;

@SuppressWarnings("NonAsciiCharacters")
@RequiredArgsConstructor
public enum ScheduleFixture {

    _7월_1일_10시부터_12시까지(_7월_1일, _10시_00분, _12시_00분),
    _7월_2일_10시부터_12시까지(_7월_2일, _10시_00분, _12시_00분);

    private final DateFixture date;
    private final TimeFixture startTime;
    private final TimeFixture endTime;

    public Schedule newInstance() {
        return new Schedule(date.getInstance(), startTime.getInstance(), endTime.getInstance());
    }
}
