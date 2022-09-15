package com.woowacourse.momo.group.domain.calendar;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import com.woowacourse.momo.fixture.calendar.DeadlineFixture;
import com.woowacourse.momo.fixture.calendar.datetime.DateFixture;
import com.woowacourse.momo.group.exception.GroupException;

class DeadlineTest {

    @DisplayName("마감 기한이 정상적으로 생성되었는지 확인한다")
    @ParameterizedTest
    @MethodSource("provideForConstruct")
    void construct(LocalDateTime dateTime) {
        Deadline deadline = new Deadline(dateTime);

        assertThat(deadline.getValue()).isEqualTo(dateTime);
    }

    private static Stream<Arguments> provideForConstruct() {
        LocalDateTime now = LocalDateTime.now();
        return Stream.of(
                Arguments.of(Named.of("1일 후", now.plusDays(1))),
                Arguments.of(Named.of("1시간 후", now.plusHours(1))),
                Arguments.of(Named.of("1분 후", now.plusMinutes(1))),
                Arguments.of(Named.of("1초 후", now.plusSeconds(1)))
        );
    }

    @DisplayName("마감 기한이 현재 시간보다 이전일 경우 예외가 발생한다")
    @ParameterizedTest
    @MethodSource("provideForDeadlineValidation")
    void deadlineMustBeBeforeNow(LocalDateTime past) {
        assertThatThrownBy(() -> new Deadline(past))
                .isInstanceOf(GroupException.class)
                .hasMessage("마감시간이 과거일 수 없습니다.");
    }

    private static Stream<Arguments> provideForDeadlineValidation() {
        LocalDateTime now = LocalDateTime.now();
        return Stream.of(
                Arguments.of(Named.of("1일 전", now.minusDays(1))),
                Arguments.of(Named.of("1시간 전", now.minusHours(1))),
                Arguments.of(Named.of("1분 전", now.minusMinutes(1))),
                Arguments.of(Named.of("1초 전", now.minusSeconds(1)))
        );
    }

    @DisplayName("마감 기한이 지났는지 확인한다")
    @ParameterizedTest
    @MethodSource("provideForIsPast")
    void isPast(Deadline deadline, boolean expected) {
        assertThat(deadline.isPast()).isEqualTo(expected);
    }

    private static Stream<Arguments> provideForIsPast() {
        LocalDateTime now = LocalDateTime.now();
        return Stream.of(
                Arguments.of(Named.of("1일 전", DeadlineFixture.newPastDeadline(now.minusDays(1))), true),
                Arguments.of(Named.of("1시간 전", DeadlineFixture.newPastDeadline(now.minusHours(1))), true),
                Arguments.of(Named.of("1분 전", DeadlineFixture.newPastDeadline(now.minusMinutes(1))), true),
                Arguments.of(Named.of("1초 전", DeadlineFixture.newPastDeadline(now.minusSeconds(1))), true),
                Arguments.of(Named.of("1초 후", DeadlineFixture.newPastDeadline(now.plusSeconds(1))), false),
                Arguments.of(Named.of("1분 후", DeadlineFixture.newPastDeadline(now.plusMinutes(1))), false)
        );
    }

    @DisplayName("마감 기한이 주어진 날짜보다 이전인지 확인한다")
    @ParameterizedTest
    @CsvSource(value = {"-1, true", "0,true", "1, false"})
    void isAfterThan(int days, boolean expected) {
        Deadline deadline = DeadlineFixture.newDeadline(1);
        assertThat(deadline.isAfterThan(DateFixture.newDate(days))).isEqualTo(expected);
    }
}
