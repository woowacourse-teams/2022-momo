package com.woowacourse.momo.service;

import com.woowacourse.momo.domain.category.Category;
import com.woowacourse.momo.repository.CategoryRepository;
import com.woowacourse.momo.service.dto.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryResponse> findAll() {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream()
                .map(CategoryResponse::from)
                .collect(Collectors.toList());
    }
}
