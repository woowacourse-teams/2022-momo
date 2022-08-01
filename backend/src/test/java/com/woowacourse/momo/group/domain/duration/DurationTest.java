package com.woowacourse.momo.group.domain.duration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import static com.woowacourse.momo.fixture.DateFixture._1일_전;
import static com.woowacourse.momo.fixture.DateFixture._1일_후;
import static com.woowacourse.momo.fixture.DateFixture._3일_후;
import static com.woowacourse.momo.fixture.DateFixture._7일_후;
import static com.woowacourse.momo.fixture.DateTimeFixture._1일_후_23시_59분;
import static com.woowacourse.momo.fixture.DateTimeFixture._3일_후_23시_59분;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.woowacourse.momo.group.exception.InvalidDurationException;

class DurationTest {

    @DisplayName("시작일은 종료일 이후가 될 수 없다")
    @Test
    void validateEndIsNotBeforeStart() {
        assertThatThrownBy(() -> new Duration(_7일_후.getInstance(), _3일_후.getInstance()))
                .isInstanceOf(InvalidDurationException.class)
                .hasMessage("시작일은 종료일 이후가 될 수 없습니다.");
    }

    @DisplayName("시작일과 종료일은 과거의 날짜가 될 수 없다")
    @Test
    void validatePastDate() {
        assertThatThrownBy(() -> new Duration(_1일_전.getInstance(), _1일_전.getInstance()))
                .isInstanceOf(InvalidDurationException.class)
                .hasMessage("시작일과 종료일은 과거의 날짜가 될 수 없습니다.");
    }

    @DisplayName("시작일 이후의 일자일 경우 True를 반환한다.")
    @Test
    void isAfterStartDate() {
        Duration duration = new Duration(_1일_후.getInstance(), _1일_후.getInstance());
        boolean actual = duration.isAfterStartDate(_3일_후_23시_59분.getInstance());

        assertThat(actual).isTrue();
    }

    @DisplayName("시작일 이후의 일자가 아닐 경우 False를 반환한다.")
    @Test
    void isNotAfterStartDate() {
        Duration duration = new Duration(_1일_후.getInstance(), _1일_후.getInstance());
        boolean actual = duration.isAfterStartDate(_1일_후_23시_59분.getInstance());

        assertThat(actual).isFalse();
    }
}
