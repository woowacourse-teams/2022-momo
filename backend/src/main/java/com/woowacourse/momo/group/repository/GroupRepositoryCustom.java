package com.woowacourse.momo.group.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.woowacourse.momo.group.domain.Group;
import com.woowacourse.momo.group.service.dto.request.GroupFindRequest;
import com.woowacourse.momo.member.domain.Member;

public interface GroupRepositoryCustom {

    Page<Group> findGroups(GroupFindRequest request, Pageable pageable);

    Page<Group> findParticipatedGroups(GroupFindRequest request, Member member, Pageable pageable);

    List<Group> findParticipatedGroups(Member member);

    Page<Group> findHostedGroups(GroupFindRequest request, Member member, Pageable pageable);
}
