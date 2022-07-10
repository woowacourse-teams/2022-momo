package com.woowacourse.momo.group.service;

import com.woowacourse.momo.group.service.dto.CategoryResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql("classpath:init.sql")
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @DisplayName("카테고리 목록을 조회한다")
    @Test
    void findAll() {
        List<CategoryResponse> actual = categoryService.findAll();

        assertThat(actual).hasSize(5);
    }
}
