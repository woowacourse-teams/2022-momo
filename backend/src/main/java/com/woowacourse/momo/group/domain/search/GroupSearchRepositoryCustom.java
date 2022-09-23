package com.woowacourse.momo.group.domain.search;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.woowacourse.momo.group.domain.Group;
import com.woowacourse.momo.member.domain.Member;

public interface GroupSearchRepositoryCustom {

    Page<Group> findGroups(SearchCondition condition, Pageable pageable);

    Page<Group> findHostedGroups(SearchCondition condition, Member member, Pageable pageable);

    Page<Group> findParticipatedGroups(SearchCondition condition, Member member, Pageable pageable);

    Page<Group> findLikedGroups(SearchCondition condition, Member member, Pageable pageable);

    List<Group> findParticipatedGroups(Member member);
}
