package com.woowacourse.momo.group.domain.calendar;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import static com.woowacourse.momo.fixture.calendar.DeadlineFixture.내일_23시_59분까지;
import static com.woowacourse.momo.fixture.calendar.DeadlineFixture.이틀후_23시_59분까지;
import static com.woowacourse.momo.fixture.calendar.DeadlineFixture.일주일후_23시_59분까지;
import static com.woowacourse.momo.fixture.calendar.DurationFixture.내일_하루동안;
import static com.woowacourse.momo.fixture.calendar.DurationFixture.이틀후부터_5일동안;
import static com.woowacourse.momo.fixture.calendar.DurationFixture.일주일후_하루동안;
import static com.woowacourse.momo.fixture.calendar.ScheduleFixture.내일_10시부터_12시까지;
import static com.woowacourse.momo.fixture.calendar.ScheduleFixture.이틀후_10시부터_12시까지;
import static com.woowacourse.momo.fixture.calendar.ScheduleFixture.일주일후_10시부터_12시까지;

import java.lang.reflect.Field;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import com.woowacourse.momo.fixture.calendar.DeadlineFixture;
import com.woowacourse.momo.fixture.calendar.DurationFixture;
import com.woowacourse.momo.fixture.calendar.ScheduleFixture;
import com.woowacourse.momo.group.exception.GroupException;

class CalendarTest {

    @DisplayName("Calendar를 정상적으로 생성한다")
    @Test
    void construct() {
        Deadline deadline = 내일_23시_59분까지.toDeadline();
        Duration duration = 이틀후부터_5일동안.toDuration();
        Schedules schedules = ScheduleFixture.toSchedules(이틀후_10시부터_12시까지);
        Calendar calendar = new Calendar(deadline, duration, schedules);

        assertAll(
                () -> assertThat(calendar.getDeadline()).isEqualTo(deadline),
                () -> assertThat(calendar.getDuration()).isEqualTo(duration),
                () -> assertThat(calendar.getSchedules()).usingRecursiveComparison()
                        .isEqualTo(schedules)
        );
    }

    private Calendar createCalendar(DeadlineFixture deadline, DurationFixture duration, ScheduleFixture... schedules) {
        return new Calendar(deadline.toDeadline(), duration.toDuration(), ScheduleFixture.toSchedules(schedules));
    }

    @DisplayName("생성 시, 마감 기한이 기간의 시작 일자보다 이후일 수 없다")
    @Test
    void validateDeadlineIsNotAfterDurationStartWhenCreate() {
        assertThatThrownBy(() -> createCalendar(이틀후_23시_59분까지, 내일_하루동안))
                .isInstanceOf(GroupException.class)
                .hasMessage("마감시간이 시작 일자 이후일 수 없습니다.");
    }

    @DisplayName("생성 시, 일정들이 기간 내에 속해있지 않을 경우 예외가 발생한다")
    @Test
    void validateSchedulesAreInDurationWhenCreate() {
        assertThatThrownBy(() -> createCalendar(내일_23시_59분까지, 이틀후부터_5일동안, 내일_10시부터_12시까지))
                .isInstanceOf(GroupException.class)
                .hasMessage("일정은 모임 기간에 포함되어야 합니다.");
    }

    @DisplayName("Calendar를 수정한다")
    @ParameterizedTest
    @MethodSource("provideForUpdate")
    void update(Deadline deadline, Duration duration, Schedules schedules) {
        Calendar calendar = createCalendar(내일_23시_59분까지, 이틀후부터_5일동안, 이틀후_10시부터_12시까지);
        calendar.update(deadline, duration, schedules);

        assertAll(
                () -> assertThat(calendar.getDeadline()).isEqualTo(deadline),
                () -> assertThat(calendar.getDuration()).isEqualTo(duration),
                () -> assertThat(calendar.getSchedules()).usingRecursiveComparison()
                        .isEqualTo(schedules)
        );
    }

    private static Stream<Arguments> provideForUpdate() {
        return Stream.of(
                Arguments.of(이틀후_23시_59분까지.toDeadline(), 일주일후_하루동안.toDuration(),
                        ScheduleFixture.toSchedules(일주일후_10시부터_12시까지)),
                Arguments.of(일주일후_23시_59분까지.toDeadline(), 일주일후_하루동안.toDuration(),
                        ScheduleFixture.toSchedules(일주일후_10시부터_12시까지))
        );
    }

    @DisplayName("수정 시, 마감 기한이 기간의 시작 일자보다 이후일 수 없다")
    @Test
    void validateDeadlineIsNotAfterDurationStartWhenUpdate() {
        Calendar calendar = createCalendar(내일_23시_59분까지, 이틀후부터_5일동안, 이틀후_10시부터_12시까지);

        Deadline deadline = 일주일후_23시_59분까지.toDeadline();
        Duration duration = 내일_하루동안.toDuration();
        Schedules schedules = ScheduleFixture.toSchedules(일주일후_10시부터_12시까지);

        assertThatThrownBy(() -> calendar.update(deadline, duration, schedules))
                .isInstanceOf(GroupException.class)
                .hasMessage("마감시간이 시작 일자 이후일 수 없습니다.");
    }

    @DisplayName("수정 시, 일정들이 기간 내에 속해있지 않을 경우 예외가 발생한다")
    @Test
    void validateSchedulesAreInDurationWhenUpdate() {
        Calendar calendar = createCalendar(내일_23시_59분까지, 이틀후부터_5일동안, 이틀후_10시부터_12시까지);

        Deadline deadline = 이틀후_23시_59분까지.toDeadline();
        Duration duration = 일주일후_하루동안.toDuration();
        Schedules schedules = ScheduleFixture.toSchedules(내일_10시부터_12시까지);

        assertThatThrownBy(() -> calendar.update(deadline, duration, schedules))
                .isInstanceOf(GroupException.class)
                .hasMessage("일정은 모임 기간에 포함되어야 합니다.");
    }

    @DisplayName("마감 기한이 지났는지 확인한다")
    @ParameterizedTest
    @CsvSource(value = {"-1,true", "1,false"})
    void isDeadlineOver(int pastDays, boolean expected) throws IllegalAccessException {
        Deadline deadline = 내일_23시_59분까지.toDeadline();
        Duration duration = 일주일후_하루동안.toDuration();
        Schedules schedules = ScheduleFixture.emptySchedules();

        Calendar calendar = new Calendar(deadline, duration, schedules);
        setDeadlinePast(calendar, pastDays);

        assertThat(calendar.isDeadlineOver()).isEqualTo(expected);
    }

    private void setDeadlinePast(Calendar calendar, int pastDays) throws IllegalAccessException {
        Field[] fields = Calendar.class.getDeclaredFields();
        fields[2].setAccessible(true);
        fields[2].set(calendar, DeadlineFixture.newDeadline(pastDays));
    }
}
