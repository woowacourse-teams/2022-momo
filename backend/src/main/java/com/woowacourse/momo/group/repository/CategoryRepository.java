package com.woowacourse.momo.group.repository;

import com.woowacourse.momo.group.domain.Category;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface CategoryRepository extends Repository<Category, Long> {

    List<Category> findAll();

    boolean existsById(Long id);
}
