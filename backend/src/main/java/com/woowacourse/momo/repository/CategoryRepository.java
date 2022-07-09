package com.woowacourse.momo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.woowacourse.momo.domain.category.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
