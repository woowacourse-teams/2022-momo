package com.woowacourse.momo.group.service;

import static com.woowacourse.momo.group.exception.GroupErrorCode.NOT_EXIST;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.group.domain.Group;
import com.woowacourse.momo.group.domain.GroupRepository;
import com.woowacourse.momo.group.exception.GroupException;
import com.woowacourse.momo.group.service.request.GroupFindRequest;
import com.woowacourse.momo.group.service.specification.GroupSpecification;
import com.woowacourse.momo.member.domain.Member;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class GroupFindService {

    private static final int DEFAULT_PAGE_SIZE = 12;
    private final GroupRepository groupRepository;
    private final GroupSpecification groupSpecification;

    public Group findGroup(Long id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new GroupException(NOT_EXIST));
    }

    public Page<Group> findGroups(GroupFindRequest request) {
        Specification<Group> specification = Specification.where(groupSpecification.initialize());
        return findGroups(specification, request);
    }

    public List<Group> findParticipatedGroups(Member member) {
        Specification<Group> specification = Specification.where(groupSpecification.filterByParticipated(member));
        return groupRepository.findAll(specification);
    }

    public Page<Group> findParticipatedGroups(GroupFindRequest request, Member member) {
        Specification<Group> specification = Specification.where(groupSpecification.filterByParticipated(member));
        return findGroups(specification, request);
    }

    public Page<Group> findHostedGroups(GroupFindRequest request, Member member) {
        Specification<Group> specification = Specification.where(groupSpecification.filterByHosted(member));
        return findGroups(specification, request);
    }

    private Page<Group> findGroups(Specification<Group> specification, GroupFindRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), DEFAULT_PAGE_SIZE);

        specification = specification.and(groupSpecification.filterByCategory(request.getCategory()))
                .and(groupSpecification.excludeFinished(request.excludeFinished()))
                .and(groupSpecification.containKeyword(request.getKeyword()))
                .and(groupSpecification.orderByDeadline(request.orderByDeadline()));

        return groupRepository.findAll(specification, pageable);
    }
}
