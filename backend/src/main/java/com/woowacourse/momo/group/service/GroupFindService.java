package com.woowacourse.momo.group.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class GroupFindService {

    private final GroupRepository groupRepository;

    public Group findGroup(Long id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new MomoException(ErrorCode.GROUP_NOT_EXIST));
    }

    public List<Group> findGroups() {
        return groupRepository.findAll();
    }

    public Page<Group> findGroups(Pageable pageable) {
        return groupRepository.findAll(pageable);
    }

    public Page<Group> findGroupsByCategory(Category category, Pageable pageable) {
        return groupRepository.findAllByCategory(category, pageable);
    }

    public List<Group> findRelatedGroups(Long memberId) {
        List<Group> participatedGroup = groupRepository.findParticipatedGroups(memberId);

        return new ArrayList<>(participatedGroup);
    }
}
