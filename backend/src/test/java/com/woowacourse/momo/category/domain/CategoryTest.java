package com.woowacourse.momo.category.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.woowacourse.momo.global.exception.exception.MomoException;

class CategoryTest {

    @DisplayName("존재하지 않는 카테고리를 조회할 수 없다")
    @Test
    void from() {
        assertThatThrownBy(() -> Category.from(0))
                .isInstanceOf(MomoException.class)
                .hasMessage("존재하지 않는 카테고리입니다.");
    }
}
