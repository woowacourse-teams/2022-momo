package com.woowacourse.momo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.domain.category.Category;
import com.woowacourse.momo.repository.CategoryRepository;
import com.woowacourse.momo.service.dto.response.category.CategoryResponse;
import com.woowacourse.momo.service.dto.response.category.CategoryResponseAssembler;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryResponse> findAll() {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream()
                .map(CategoryResponseAssembler::categoryResponse)
                .collect(Collectors.toList());
    }
}
