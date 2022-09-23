package com.woowacourse.momo.group.domain.calendar;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import static com.woowacourse.momo.fixture.calendar.DeadlineFixture.내일_23시_59분까지;
import static com.woowacourse.momo.fixture.calendar.DurationFixture.이틀후_하루동안;
import static com.woowacourse.momo.fixture.calendar.datetime.DateFixture.내일;
import static com.woowacourse.momo.fixture.calendar.datetime.DateFixture.어제;
import static com.woowacourse.momo.fixture.calendar.datetime.DateFixture.오늘;
import static com.woowacourse.momo.fixture.calendar.datetime.DateFixture.이틀후;
import static com.woowacourse.momo.fixture.calendar.datetime.DateFixture.일주일후;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.woowacourse.momo.fixture.calendar.DeadlineFixture;
import com.woowacourse.momo.group.exception.GroupException;

class DurationTest {

    @DisplayName("정상적인 기간 생성에 대해서는 에러가 발생하지 않는다")
    @ParameterizedTest
    @MethodSource("provideForConstruct")
    void construct(LocalDate start, LocalDate end) {
        Duration duration = new Duration(start, end);

        assertAll(
                () -> assertThat(duration.getStartDate()).isEqualTo(start),
                () -> assertThat(duration.getEndDate()).isEqualTo(end)
        );
    }

    private static Stream<Arguments> provideForConstruct() {
        return Stream.of(
                Arguments.of(
                        Named.of("오늘로부터 이틀 후", 이틀후.toDate()),
                        Named.of("오늘로부터 이틀 후", 이틀후.toDate())),
                Arguments.of(
                        Named.of("오늘로부터 이틀 후", 이틀후.toDate()),
                        Named.of("오늘로부터 일주일 후", 일주일후.toDate()))
        );
    }

    @DisplayName("시작일은 종료일 이후가 될 수 없다")
    @Test
    void validateStartIsBeforeEnd() {
        assertThatThrownBy(() -> new Duration(일주일후.toDate(), 이틀후.toDate()))
                .isInstanceOf(GroupException.class)
                .hasMessage("기간의 시작일은 종료일 이전이어야 합니다.");
    }

    @DisplayName("시작일과 종료일은 과거의 날짜가 될 수 없다")
    @ParameterizedTest
    @MethodSource("provideForValidateDatesAreNotPast")
    void validateDatesAreNotPast(LocalDate start, LocalDate end) {
        assertThatThrownBy(() -> new Duration(start, end))
                .isInstanceOf(GroupException.class)
                .hasMessage("시작일과 종료일은 과거일 수 없습니다.");
    }

    private static Stream<Arguments> provideForValidateDatesAreNotPast() {
        Named<LocalDate> yesterday = Named.of("어제", 어제.toDate());
        Named<LocalDate> tomorrow = Named.of("내일", 내일.toDate());
        return Stream.of(
                Arguments.of(yesterday, yesterday),
                Arguments.of(yesterday, tomorrow)
        );
    }

    @DisplayName("시작일 이후의 일자가 아닌지 확인한다")
    @ParameterizedTest
    @MethodSource("provideForIsStartBeforeDeadline")
    void isStartBeforeDeadline(Deadline deadline, Duration duration, boolean expected) {
        assertThat(duration.isStartBeforeDeadline(deadline)).isEqualTo(expected);
    }

    private static Stream<Arguments> provideForIsStartBeforeDeadline() {
        return Stream.of(
                Arguments.of(
                        Named.of("어제까지", DeadlineFixture.newPastDeadline(LocalDateTime.now().minusDays(1))),
                        Named.of("이틀후 하루동안", 이틀후_하루동안.toDuration()),
                        false),
                Arguments.of(
                        Named.of("내일까지", 내일_23시_59분까지.toDeadline()),
                        Named.of("오늘부터 일주일동안", new Duration(오늘.toDate(), 일주일후.toDate())),
                        true)
        );
    }
}
