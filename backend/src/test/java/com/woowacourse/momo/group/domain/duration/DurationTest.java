package com.woowacourse.momo.group.domain.duration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import static com.woowacourse.momo.fixture.DateFixture._7월_1일;
import static com.woowacourse.momo.fixture.DateFixture._7월_2일;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.woowacourse.momo.group.exception.InvalidDurationException;

class DurationTest {

    @DisplayName("시작일은 종료일 이후가 될 수 없다")
    @Test
    void validateEndIsNotBeforeStart() {
        assertThatThrownBy(() -> new Duration(_7월_2일.getInstance(), _7월_1일.getInstance()))
                .isInstanceOf(InvalidDurationException.class);
    }
}
