package com.woowacourse.momo.group.domain.calendar;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import static com.woowacourse.momo.fixture.calendar.datetime.DateFixture.내일;
import static com.woowacourse.momo.fixture.calendar.datetime.DateFixture.어제;
import static com.woowacourse.momo.fixture.calendar.datetime.DateFixture.이틀후;
import static com.woowacourse.momo.fixture.calendar.datetime.DateFixture.일주일후;
import static com.woowacourse.momo.fixture.calendar.datetime.DateTimeFixture.내일_23시_59분;
import static com.woowacourse.momo.fixture.calendar.datetime.DateTimeFixture.이틀후_23시_59분;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.woowacourse.momo.global.exception.exception.MomoException;

class DurationTest {

    @DisplayName("정상적인 기간 생성에 대해서는 에러가 발생하지 않는다")
    @Test
    void create() {
        assertDoesNotThrow(
                () -> new Duration(이틀후.getInstance(), 일주일후.getInstance())
        );
    }

    @DisplayName("시작일은 종료일 이후가 될 수 없다")
    @Test
    void validateEndIsNotBeforeStart() {
        assertThatThrownBy(() -> new Duration(일주일후.getInstance(), 이틀후.getInstance()))
                .isInstanceOf(MomoException.class)
                .hasMessage("기간의 시작일은 종료일 이전이어야 합니다.");
    }

    @DisplayName("시작일과 종료일은 과거의 날짜가 될 수 없다")
    @Test
    void validatePastDate() {
        assertThatThrownBy(() -> new Duration(어제.getInstance(), 어제.getInstance()))
                .isInstanceOf(MomoException.class)
                .hasMessage("시작일과 종료일은 과거일 수 없습니다.");
    }

    @DisplayName("시작일 이후의 일자인지 확인한다")
    @ParameterizedTest
    @MethodSource("provideIsAfterStartDateArguments")
    void isAfterStartDate(LocalDateTime localDateTime, boolean expected) {
        Duration duration = new Duration(내일.getInstance(), 내일.getInstance());

        assertThat(duration.isAfterStartDate(localDateTime)).isEqualTo(expected);
    }

    private static Stream<Arguments> provideIsAfterStartDateArguments() {
        return Stream.of(
                Arguments.of(이틀후_23시_59분.getInstance(), Boolean.TRUE),
                Arguments.of(내일_23시_59분.getInstance(), Boolean.FALSE)
        );
    }
}
