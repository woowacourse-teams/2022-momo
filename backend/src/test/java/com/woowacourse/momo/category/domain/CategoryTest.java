package com.woowacourse.momo.category.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.woowacourse.momo.category.exception.CategoryException;

class CategoryTest {

    @DisplayName("존재하지 않는 카테고리를 조회할 수 없다")
    @Test
    void from() {
        assertThatThrownBy(() -> Category.from(0))
                .isInstanceOf(CategoryException.class)
                .hasMessage("존재하지 않는 카테고리입니다.");
    }
}
