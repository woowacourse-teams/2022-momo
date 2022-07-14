package com.woowacourse.momo.category.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CategoryTest {

    @DisplayName("존재하지 않는 카테고리를 조회할 수 없다")
    @Test
    void from() {
        assertThatThrownBy(() -> Category.from(0))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("카테고리를 찾을 수 없습니다.");
    }
}
