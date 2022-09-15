package com.woowacourse.momo.group.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.woowacourse.momo.group.domain.Group;
import com.woowacourse.momo.group.service.dto.request.GroupFindRequest;

public interface GroupRepositoryCustom {

    Page<Group> findGroups(GroupFindRequest request, Pageable pageable);

//    Page<Group> findParticipatedGroups(GroupFindRequest request, Member member, Pageable pageable);
//
//    Page<Group> findHostedGroups(GroupFindRequest request, Member member, Pageable pageable);
}
