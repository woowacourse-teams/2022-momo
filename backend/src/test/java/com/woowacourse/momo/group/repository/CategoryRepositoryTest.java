package com.woowacourse.momo.group.repository;

import com.woowacourse.momo.group.domain.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
@Sql("classpath:init.sql")
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @DisplayName("카테고리 목록을 조회한다")
    @Test
    void findAll() {
        List<Category> categories = categoryRepository.findAll();

        assertThat(categories).hasSize(10);
    }

    @DisplayName("식별자를 가진 데이터가 존재하지 않으면 True를 반환한다.")
    @Test
    void existsByExistId() {
        Long id = 1L;
        boolean actual = categoryRepository.existsById(id);

        assertThat(actual).isTrue();
    }

    @DisplayName("식별자를 가진 데이터가 존재하면 False를 반환한다.")
    @Test
    void existsByNotExistId() {
        Long id = 100L;
        boolean actual = categoryRepository.existsById(id);

        assertThat(actual).isFalse();
    }
}
