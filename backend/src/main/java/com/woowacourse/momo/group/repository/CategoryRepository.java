package com.woowacourse.momo.group.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.woowacourse.momo.group.domain.Category;

public interface CategoryRepository extends Repository<Category, Long> {

    List<Category> findAll();

    boolean existsById(Long id);
}
