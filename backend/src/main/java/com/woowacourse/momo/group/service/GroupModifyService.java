package com.woowacourse.momo.group.service;

import static com.woowacourse.momo.group.exception.GroupErrorCode.MEMBER_IS_NOT_HOST;

import java.util.function.BiConsumer;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.group.domain.Group;
import com.woowacourse.momo.group.domain.GroupName;
import com.woowacourse.momo.group.domain.GroupRepository;
import com.woowacourse.momo.group.domain.calendar.Calendar;
import com.woowacourse.momo.group.domain.participant.Capacity;
import com.woowacourse.momo.group.exception.GroupException;
import com.woowacourse.momo.group.service.dto.request.GroupRequest;
import com.woowacourse.momo.group.service.dto.request.GroupUpdateRequest;
import com.woowacourse.momo.group.service.dto.request.LocationRequest;
import com.woowacourse.momo.group.service.dto.response.GroupIdResponse;
import com.woowacourse.momo.group.service.dto.response.GroupResponseAssembler;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.service.MemberFindService;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class GroupModifyService {

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
    public void update(Long hostId, Long groupId, GroupUpdateRequest request) {
        ifMemberIsHost(hostId, groupId, (host, group) -> updateGroup(group, request));
    }

    private void updateGroup(Group group, GroupUpdateRequest request) {
        GroupName groupName = request.getName();
        Category category = request.getCategory();
        Capacity capacity = request.getCapacity();
        Calendar calendar = request.getCalendar();

        group.update(capacity, calendar, groupName, category, request.getDescription());
    }

    @Transactional
    public void updateLocation(Long hostId, Long groupId, LocationRequest request) {
        ifMemberIsHost(hostId, groupId, (host, group) -> group.updateLocation(request.getLocation()));
    }

    @Transactional
    public void closeEarly(Long hostId, Long groupId) {
        ifMemberIsHost(hostId, groupId, (host, group) -> group.closeEarly());
    }

    @Transactional
    public void delete(Long hostId, Long groupId) {
        ifMemberIsHost(hostId, groupId, (host, group) -> {
            group.validateGroupIsDeletable();
            groupRepository.deleteById(groupId);
        });
    }

    private void ifMemberIsHost(Long hostId, Long groupId, BiConsumer<Member, Group> consumer) {
        Group group = groupFindService.findGroup(groupId);
        Member host = memberFindService.findMember(hostId);
        validateMemberIsHost(group, host);

        consumer.accept(host, group);
    }

    private void validateMemberIsHost(Group group, Member member) {
        if (group.isNotHost(member)) {
            throw new GroupException(MEMBER_IS_NOT_HOST);
        }
    }
}
