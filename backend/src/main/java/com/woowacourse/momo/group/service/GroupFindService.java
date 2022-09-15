package com.woowacourse.momo.group.service;

import static com.woowacourse.momo.group.exception.GroupErrorCode.NOT_EXIST;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.group.domain.Group;
import com.woowacourse.momo.group.domain.GroupRepository;
import com.woowacourse.momo.group.exception.GroupException;
import com.woowacourse.momo.group.infrastructure.querydsl.GroupFindRepository;
import com.woowacourse.momo.member.domain.Member;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class GroupFindService {

    private final GroupRepository groupRepository;
    private final GroupFindRepository groupFindRepository;

    public Group findGroup(Long id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new GroupException(NOT_EXIST));
    }

    public List<Group> findParticipatedGroups(Member member) {
        return groupFindRepository.findParticipatedGroups(member);
    }
}
