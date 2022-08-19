package com.woowacourse.momo.group.domain.duration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import static com.woowacourse.momo.fixture.DateFixture.내일;
import static com.woowacourse.momo.fixture.DateFixture.어제;
import static com.woowacourse.momo.fixture.DateFixture.이틀후;
import static com.woowacourse.momo.fixture.DateFixture.일주일후;
import static com.woowacourse.momo.fixture.DateTimeFixture.내일_23시_59분;
import static com.woowacourse.momo.fixture.DateTimeFixture.이틀후_23시_59분;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

    @DisplayName("시작일 이후의 일자일 경우 True를 반환한다.")
    @Test
    void isAfterStartDate() {
        Duration duration = new Duration(내일.getInstance(), 내일.getInstance());
        boolean actual = duration.isAfterStartDate(이틀후_23시_59분.getInstance());

        assertThat(actual).isTrue();
    }

    @DisplayName("시작일 이후의 일자가 아닐 경우 False를 반환한다.")
    @Test
    void isNotAfterStartDate() {
        Duration duration = new Duration(내일.getInstance(), 내일.getInstance());
        boolean actual = duration.isAfterStartDate(내일_23시_59분.getInstance());

        assertThat(actual).isFalse();
    }
}
