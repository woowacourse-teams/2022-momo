package com.woowacourse.momo.group.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.group.domain.group.Group;
import com.woowacourse.momo.group.domain.group.GroupRepository;
import com.woowacourse.momo.group.domain.schedule.Schedule;
import com.woowacourse.momo.group.exception.NotFoundGroupException;
import com.woowacourse.momo.group.service.dto.request.GroupRequest;
import com.woowacourse.momo.group.service.dto.request.GroupRequestAssembler;
import com.woowacourse.momo.group.service.dto.request.GroupUpdateRequest;
import com.woowacourse.momo.group.service.dto.response.GroupResponse;
import com.woowacourse.momo.group.service.dto.response.GroupResponseAssembler;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;
import com.woowacourse.momo.member.exception.NotFoundMemberException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public long create(GroupRequest groupRequest) {
        Group group = groupRepository.save(GroupRequestAssembler.group(groupRequest));

        return group.getId();
    }

    public GroupResponse findById(Long id) {
        Group group = findGroup(id);

        return convertToGroupResponse(group);
    }

    private Group findGroup(Long id) {
        return groupRepository.findById(id)
                .orElseThrow(NotFoundGroupException::new);
    }

    private GroupResponse convertToGroupResponse(Group group) {
        Member host = memberRepository.findById(group.getHostId())
                .orElseThrow(NotFoundMemberException::new);

        return GroupResponseAssembler.groupResponse(group, host);
    }

    public List<GroupResponse> findAll() {
        List<Group> groups = groupRepository.findAll();
        return groups.stream()
                .map(this::convertToGroupResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void update(Long groupId, GroupUpdateRequest request) {
        Group group = findGroup(groupId);
        List<Schedule> schedules = GroupRequestAssembler.schedules(request.getSchedules());

        group.update(request.getName(), Category.from(request.getCategoryId()),
                GroupRequestAssembler.duration(request.getDuration()), request.getDeadline(), schedules,
                request.getLocation(), request.getDescription());
    }

    @Transactional
    public void delete(Long groupId) {
        groupRepository.deleteById(groupId);
    }
}
