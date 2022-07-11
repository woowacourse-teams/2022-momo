package com.woowacourse.momo.unit.category.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.woowacourse.momo.category.domain.category.Category;
import com.woowacourse.momo.category.domain.category.CategoryRepository;

@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @DisplayName("식별자를 통해 카테고리를 조회한다")
    @Test
    void findById() {
        Category expected = categoryRepository.save(new Category("카테고리"));

        Optional<Category> actual = categoryRepository.findById(expected.getId());

        assertThat(actual).isPresent();
        assertThat(actual.get()).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @DisplayName("카테고리 목록을 조회한다")
    @ParameterizedTest
    @ValueSource(ints = {3})
    void findAll(int count) {
        List<Category> expected = IntStream.rangeClosed(0, count)
                .mapToObj(i -> categoryRepository.save(new Category("카테고리" + i)))
                .collect(Collectors.toList());

        List<Category> actual = categoryRepository.findAll();

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }
}
