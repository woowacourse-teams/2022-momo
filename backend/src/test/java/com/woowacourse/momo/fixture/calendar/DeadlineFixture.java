package com.woowacourse.momo.fixture.calendar;

import static com.woowacourse.momo.fixture.calendar.datetime.DateTimeFixture.내일_23시_59분;
import static com.woowacourse.momo.fixture.calendar.datetime.DateTimeFixture.이틀후_23시_59분;
import static com.woowacourse.momo.fixture.calendar.datetime.DateTimeFixture.일주일후_23시_59분;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

import com.woowacourse.momo.fixture.calendar.datetime.DateTimeFixture;
import com.woowacourse.momo.group.domain.calendar.Deadline;
import com.woowacourse.momo.group.service.dto.request.calendar.DeadlineRequest;

@SuppressWarnings("NonAsciiCharacters")
public enum DeadlineFixture {

    내일_23시_59분까지(내일_23시_59분),
    이틀후_23시_59분까지(이틀후_23시_59분),
    일주일후_23시_59분까지(일주일후_23시_59분),
    ;

    private final Deadline instance;

    DeadlineFixture(DateTimeFixture dateTime) {
        this.instance = new Deadline(dateTime.toDateTime());
    }

    public Deadline toDeadline() {
        return instance;
    }

    public static Deadline newDeadline(int days) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime dateTime = now.plusDays(days);

        if (dateTime.isBefore(now)) {
            return newPastDeadline(days);
        }
        return new Deadline(dateTime);
    }

    private static Deadline newPastDeadline(int days) {
        return newPastDeadline(LocalDateTime.now().plusDays(days));
    }

    public static Deadline newPastDeadline(LocalDateTime past) {
        try {
            LocalDateTime future = LocalDateTime.now().plusDays(1);
            Deadline deadline = new Deadline(future);

            Field[] fields = Deadline.class.getDeclaredFields();
            fields[0].setAccessible(true);
            fields[0].set(deadline, past);
            return deadline;
        } catch (IllegalAccessException e) {
            throw new RuntimeException();
        }
    }

    public DeadlineRequest toRequest() {
        return new DeadlineRequest(instance.getValue());
    }

    public LocalDateTime toApiRequest() {
        return instance.getValue();
    }

    public LocalDateTime getValue() {
        return instance.getValue();
    }
}
