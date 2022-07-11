package com.woowacourse.momo.category.domain;

import java.util.List;

import org.springframework.data.repository.Repository;

public interface CategoryRepository extends Repository<Category, Long> {

    List<Category> findAll();

    boolean existsById(Long id);
}
