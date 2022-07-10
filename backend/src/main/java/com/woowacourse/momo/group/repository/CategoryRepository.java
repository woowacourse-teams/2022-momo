package com.woowacourse.momo.group.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.woowacourse.momo.group.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
