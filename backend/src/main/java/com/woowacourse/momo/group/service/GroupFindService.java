package com.woowacourse.momo.group.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

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

    public Group findById(Long id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new MomoException(ErrorCode.GROUP_NOT_EXIST));
    }

    public Page<Group> findAll(GroupFindRequest request) {
        Specification<Group> specification = Specification.where(GroupSpecification.initialize());
        return findAll(specification, request);
    }

    public List<Group> findAllThatParticipated(Long memberId) {
        Specification<Group> specification = Specification.where(GroupSpecification.filterByParticipated(memberId));
        return groupRepository.findAll(specification);
    }

    public Page<Group> findAllThatParticipated(GroupFindRequest request, Long memberId) {
        Specification<Group> specification = Specification.where(GroupSpecification.filterByParticipated(memberId));
        return findAll(specification, request);
    }

    public Page<Group> findAllThatHosted(GroupFindRequest request, Long memberId) {
        Specification<Group> specification = Specification.where(GroupSpecification.filterByHosted(memberId));
        return findAll(specification, request);
    }

    private Page<Group> findAll(Specification<Group> specification, GroupFindRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), DEFAULT_PAGE_SIZE);

        specification = specification.and(GroupSpecification.filterByCategory(request.getCategory()))
                .and(GroupSpecification.excludeFinishedRecruitment(request.getExcludeFinished()))
                .and(GroupSpecification.containKeyword(request.getKeyword()))
                .and(GroupSpecification.orderByDeadline(request.getOrderByDeadline()));

        return groupRepository.findAll(specification, pageable);
    }
}
