package com.woowacourse.momo.group.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.global.exception.exception.ErrorCode;
import com.woowacourse.momo.global.exception.exception.MomoException;
import com.woowacourse.momo.group.domain.Group;
import com.woowacourse.momo.group.domain.GroupName;
import com.woowacourse.momo.group.domain.GroupRepository;
import com.woowacourse.momo.group.domain.calendar.Calendar;
import com.woowacourse.momo.group.domain.participant.Capacity;
import com.woowacourse.momo.group.service.request.GroupRequest;
import com.woowacourse.momo.group.service.response.GroupIdResponse;
import com.woowacourse.momo.group.service.response.GroupResponseAssembler;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.service.MemberFindService;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class GroupManageService {

    private final MemberFindService memberFindService;
    private final GroupFindService groupFindService;
    private final GroupRepository groupRepository;

    @Transactional
    public GroupIdResponse create(Long memberId, GroupRequest request) {
        Member host = memberFindService.findMember(memberId);
        Group group = new Group(host, request.getCapacity(), request.getCalendar(), request.getName(),
                request.getCategory(), request.getLocation(), request.getDescription());
        Group savedGroup = groupRepository.save(group);

        return GroupResponseAssembler.groupIdResponse(savedGroup);
    }

    @Transactional
    public void update(Long hostId, Long groupId, GroupRequest request) {
        Group group = groupFindService.findGroup(groupId);
        Member host = memberFindService.findMember(hostId);

        updateGroup(group, host, request);
    }

    @Transactional
    public void closeEarly(Long hostId, Long groupId) {
        Group group = groupFindService.findGroup(groupId);
        Member member = memberFindService.findMember(hostId);

        validateMemberIsHost(group, member);
        group.closeEarly();
    }

    @Transactional
    public void delete(Long hostId, Long groupId) {
        Group group = groupFindService.findGroup(groupId);
        Member member = memberFindService.findMember(hostId);

        validateMemberIsHost(group, member);
        group.validateGroupIsDeletable();

        groupRepository.deleteById(groupId);
    }

    private void updateGroup(Group group, Member member, GroupRequest request) {
        GroupName groupName = request.getName();
        Category category = request.getCategory();
        Capacity capacity = request.getCapacity();
        Calendar calendar = request.getCalendar();

        validateMemberIsHost(group, member);
        group.update(groupName, category, capacity, calendar, request.getLocation(), request.getDescription());
    }

    private void validateMemberIsHost(Group group, Member member) {
        if (group.isNotHost(member)) {
            throw new MomoException(ErrorCode.AUTH_DELETE_NO_HOST);
        }
    }
}
