package com.woowacourse.momo.group.domain.search;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.woowacourse.momo.group.domain.Group;
import com.woowacourse.momo.group.domain.search.dto.GroupSummaryRepositoryResponse;
import com.woowacourse.momo.member.domain.Member;

public interface GroupSearchRepositoryCustom {

    Page<GroupSummaryRepositoryResponse> findGroups(SearchCondition condition, Pageable pageable);

    Page<GroupSummaryRepositoryResponse> findHostedGroups(SearchCondition condition, Member member, Pageable pageable);

    Page<GroupSummaryRepositoryResponse> findParticipatedGroups(SearchCondition condition, Member member,
                                                                Pageable pageable);

    Page<GroupSummaryRepositoryResponse> findLikedGroups(SearchCondition condition, Long memberId, Pageable pageable);

    List<Group> findParticipatedGroups(Member member);
}
