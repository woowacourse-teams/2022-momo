package com.woowacourse.momo.service;

import com.woowacourse.momo.domain.group.Group;
import com.woowacourse.momo.domain.member.Member;
import com.woowacourse.momo.repository.GroupRepository;
import com.woowacourse.momo.repository.MemberRepository;
import com.woowacourse.momo.service.dto.request.GroupRequest;
import com.woowacourse.momo.service.dto.response.GroupResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public long create(GroupRequest groupRequest) {
        Group group = groupRepository.save(groupRequest.toEntity());
        return group.getId();
    }

    public GroupResponse findById(Long id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 모임입니다."));

        return convertToGroupResponse(group);
    }

    private GroupResponse convertToGroupResponse(Group group) {
        Member host = memberRepository.findById(group.getHostId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        return GroupResponse.toResponse(group, host);
    }

    public List<GroupResponse> findAll() {
        List<Group> groups = groupRepository.findAll();
        return groups.stream()
                .map(this::convertToGroupResponse)
                .collect(Collectors.toList());
    }
}
