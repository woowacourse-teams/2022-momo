package com.woowacourse.momo.group.infrastructure.querydsl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.woowacourse.momo.group.domain.Group;
import com.woowacourse.momo.member.domain.Member;

public interface GroupFindRepositoryCustom {

    Page<Group> findGroups(FindCondition condition, Pageable pageable);

    Page<Group> findHostedGroups(FindCondition condition, Member member, Pageable pageable);

    Page<Group> findParticipatedGroups(FindCondition condition, Member member, Pageable pageable);

    List<Group> findParticipatedGroups(Member member);
}
