package com.woowacourse.momo.fixture.calendar;

import static com.woowacourse.momo.fixture.calendar.datetime.DateFixture.내일;
import static com.woowacourse.momo.fixture.calendar.datetime.DateFixture.이틀후;
import static com.woowacourse.momo.fixture.calendar.datetime.DateFixture.일주일후;

import java.time.LocalDate;

import com.woowacourse.momo.fixture.calendar.datetime.DateFixture;
import com.woowacourse.momo.group.controller.dto.request.calendar.DurationApiRequest;
import com.woowacourse.momo.group.domain.calendar.Duration;
import com.woowacourse.momo.group.service.dto.request.calendar.DurationRequest;

@SuppressWarnings("NonAsciiCharacters")
public enum DurationFixture {

    내일_하루동안(내일, 내일),
    이틀후_하루동안(이틀후, 이틀후),
    이틀후부터_5일동안(이틀후, 일주일후),
    내일부터_일주일동안(내일, 일주일후),
    일주일후_하루동안(일주일후, 일주일후),
    ;

    private final Duration instance;

    DurationFixture(DateFixture start, DateFixture end) {
        this.instance = new Duration(start.toDate(), end.toDate());
    }

    public Duration toDuration() {
        return instance;
    }

    public DurationRequest toRequest() {
        return new DurationRequest(instance.getStartDate(), instance.getEndDate());
    }

    public DurationApiRequest toApiRequest() {
        return new DurationApiRequest(instance.getStartDate(), instance.getEndDate());
    }

    public LocalDate getStartDate() {
        return instance.getStartDate();
    }

    public LocalDate getEndDate() {
        return instance.getEndDate();
    }
}
