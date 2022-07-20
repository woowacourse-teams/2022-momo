package com.woowacourse.momo.group.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.group.domain.group.Group;
import com.woowacourse.momo.group.domain.group.GroupRepository;
import com.woowacourse.momo.group.exception.NotFoundGroupException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class GroupFindService {

    private final GroupRepository groupRepository;

    public Group findGroup(Long id) {
        return groupRepository.findById(id)
                .orElseThrow(NotFoundGroupException::new);
    }

    public List<Group> findGroups() {
        return groupRepository.findAll();
    }
}
