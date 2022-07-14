package com.woowacourse.momo.group.fixture;

import static com.woowacourse.momo.group.fixture.GroupFixture._10시_00분;
import static com.woowacourse.momo.group.fixture.GroupFixture._12시_00분;
import static com.woowacourse.momo.group.fixture.GroupFixture._7월_1일;
import static com.woowacourse.momo.group.fixture.GroupFixture._7월_2일;

import java.time.LocalDate;
import java.time.LocalTime;

import com.woowacourse.momo.group.domain.schedule.Schedule;

public enum ScheduleFixture {

    _7월_1일_10시부터_12시까지(_7월_1일, _10시_00분, _12시_00분),
    _7월_2일_10시부터_12시까지(_7월_2일, _10시_00분, _12시_00분)
    ;

    private final LocalDate date;
    private final LocalTime startTime;
    private final LocalTime endTime;

    ScheduleFixture(LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Schedule newInstance() {
        return new Schedule(date, startTime, endTime);
    }
}
