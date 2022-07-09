package com.woowacourse.momo.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.woowacourse.momo.domain.category.Category;

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
    @Test
    void findAll() {
        Category category1 = categoryRepository.save(new Category("카테고리1"));
        Category category2 = categoryRepository.save(new Category("카테고리2"));

        List<Category> categories = categoryRepository.findAll();

        assertThat(categories).contains(category1, category2);
    }
}
