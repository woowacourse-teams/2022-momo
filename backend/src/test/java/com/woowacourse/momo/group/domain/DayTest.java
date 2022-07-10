package com.woowacourse.momo.group.domain;

import com.woowacourse.momo.group.exception.InvalidDayException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DayTest {

    @DisplayName("요일에 해당하는 문자를 통해 알맞은 요일을 찾는다")
    @Test
    void from() {
        String value = "월";

        Day actual = Day.from(value);
        assertThat(actual).isEqualTo(Day.MONDAY);
    }

    @DisplayName("문자에 해당하는 인스턴스가 없을 경우 예외가 발생한다")
    @Test
    void fromWithInvalidValue() {
        String value = "잘못된 값";

        assertThatThrownBy(() -> Day.from(value))
                .isInstanceOf(InvalidDayException.class);
    }
}
