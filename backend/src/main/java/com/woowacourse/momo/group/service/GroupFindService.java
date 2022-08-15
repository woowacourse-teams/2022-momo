package com.woowacourse.momo.group.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.globalException.exception.ErrorCode;
import com.woowacourse.momo.globalException.exception.MomoException;
import com.woowacourse.momo.global.exception.exception.ErrorCode;
import com.woowacourse.momo.global.exception.exception.MomoException;
import com.woowacourse.momo.group.domain.group.Group;
import com.woowacourse.momo.group.domain.group.GroupRepository;
import com.woowacourse.momo.group.domain.group.GroupSpecification;
import com.woowacourse.momo.group.service.dto.request.GroupFindRequest;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class GroupFindService {

    private static final int DEFAULT_PAGE_SIZE = 12;
    private final GroupRepository groupRepository;

    public Group findGroup(Long id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new MomoException(ErrorCode.GROUP_NOT_EXIST));
    }

    public Page<Group> findAll(GroupFindRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), DEFAULT_PAGE_SIZE);
        Specification<Group> specification = Specification.where(GroupSpecification.initialize());
        if (request.getCategory() != null) {
            Category category = Category.from(request.getCategory());
            specification = specification.and(GroupSpecification.filterByCategory(category));
        }
        if (request.getExcludeFinished() != null) {
            specification = specification.and(GroupSpecification.excludeFinishedRecruitment());
        }
        if (request.getKeyword() != null) {
            specification = specification.and(GroupSpecification.containKeyword(request.getKeyword()));
        }
        if (request.getOrderByDeadline() != null) {
            specification = specification.and(GroupSpecification.orderByDeadline());
        } else {
            specification = specification.and(GroupSpecification.orderByIdDesc());
        }

        return groupRepository.findAll(specification, pageable);
    }

    public Page<Group> findAllThatParticipated(GroupFindRequest request, Long memberId) {
        Pageable pageable = PageRequest.of(request.getPage(), DEFAULT_PAGE_SIZE);
        Specification<Group> specification = Specification.where(GroupSpecification.filterByParticipated(memberId));
        if (request.getCategory() != null) {
            Category category = Category.from(request.getCategory());
            specification = specification.and(GroupSpecification.filterByCategory(category));
        }
        if (request.getExcludeFinished() != null) {
            specification = specification.and(GroupSpecification.excludeFinishedRecruitment());
        }
        if (request.getKeyword() != null) {
            specification = specification.and(GroupSpecification.containKeyword(request.getKeyword()));
        }
        if (request.getOrderByDeadline() != null) {
            specification = specification.and(GroupSpecification.orderByDeadline());
        } else {
            specification = specification.and(GroupSpecification.orderByIdDesc());
        }

        return groupRepository.findAll(specification, pageable);
    }
}
