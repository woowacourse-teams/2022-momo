package com.woowacourse.momo.group.service;

import static com.woowacourse.momo.global.exception.exception.ErrorCode.GROUP_SCHEDULE_NOT_RANGE_DURATION;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.global.exception.exception.MomoException;
import com.woowacourse.momo.group.domain.calendar.Deadline;
import com.woowacourse.momo.group.domain.calendar.Duration;
import com.woowacourse.momo.group.domain.calendar.Schedule;
import com.woowacourse.momo.group.domain.group.Capacity;
import com.woowacourse.momo.group.domain.group.Group;
import com.woowacourse.momo.group.domain.group.GroupName;
import com.woowacourse.momo.group.domain.group.GroupRepository;
import com.woowacourse.momo.group.service.dto.request.GroupFindRequest;
import com.woowacourse.momo.group.service.dto.request.GroupRequest;
import com.woowacourse.momo.group.service.dto.request.GroupRequestAssembler;
import com.woowacourse.momo.group.service.dto.request.GroupUpdateRequest;
import com.woowacourse.momo.group.service.dto.response.GroupIdResponse;
import com.woowacourse.momo.group.service.dto.response.GroupPageResponse;
import com.woowacourse.momo.group.service.dto.response.GroupResponse;
import com.woowacourse.momo.group.service.dto.response.GroupResponseAssembler;
import com.woowacourse.momo.group.service.dto.response.GroupSummaryResponse;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.service.MemberFindService;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class GroupService {

    private final MemberFindService memberFindService;
    private final GroupFindService groupFindService;
    private final GroupRepository groupRepository;

    @Transactional
    public GroupIdResponse create(Long memberId, GroupRequest groupRequest) {
        Member host = memberFindService.findMember(memberId);
        Group group = groupRepository.save(GroupRequestAssembler.group(host, groupRequest));

        return GroupResponseAssembler.groupIdResponse(group);
    }

    public GroupResponse findGroup(Long id) {
        Group group = groupFindService.findGroup(id);
        return GroupResponseAssembler.groupResponse(group);
    }

    public GroupPageResponse findGroups(GroupFindRequest request) {
        Page<Group> groups = groupFindService.findGroups(request);
        List<Group> groupsOfPage = groups.getContent();
        List<GroupSummaryResponse> summaries = GroupResponseAssembler.groupSummaryResponses(groupsOfPage);

        return GroupResponseAssembler.groupPageResponse(summaries, groups.hasNext(), request.getPage());
    }

    public GroupPageResponse findParticipatedGroups(GroupFindRequest request, Long memberId) {
        Member member = memberFindService.findMember(memberId);
        Page<Group> groups = groupFindService.findParticipatedGroups(request, member);
        List<Group> groupsOfPage = groups.getContent();
        List<GroupSummaryResponse> summaries = GroupResponseAssembler.groupSummaryResponses(groupsOfPage);

        return GroupResponseAssembler.groupPageResponse(summaries, groups.hasNext(), request.getPage());
    }

    public GroupPageResponse findHostedGroups(GroupFindRequest request, Long memberId) {
        Member member = memberFindService.findMember(memberId);
        Page<Group> groups = groupFindService.findHostedGroups(request, member);
        List<Group> groupsOfPage = groups.getContent();
        List<GroupSummaryResponse> summaries = GroupResponseAssembler.groupSummaryResponses(groupsOfPage);

        return GroupResponseAssembler.groupPageResponse(summaries, groups.hasNext(), request.getPage());
    }

    @Transactional
    public void update(Long hostId, Long groupId, GroupUpdateRequest request) {
        Group group = groupFindService.findGroup(groupId);
        Member host = memberFindService.findMember(hostId);

        GroupName groupName = GroupRequestAssembler.groupName(request);
        Capacity capacity = GroupRequestAssembler.capacity(request);
        Duration duration = GroupRequestAssembler.duration(request.getDuration());
        Deadline deadline = GroupRequestAssembler.deadline(request);
        List<Schedule> schedules = GroupRequestAssembler.schedules(request.getSchedules());

        group.update(groupName, host, Category.from(request.getCategoryId()), capacity,
                duration, deadline, schedules, request.getLocation(), request.getDescription());
    }

    @Transactional
    public void closeEarly(Long hostId, Long groupId) {
        Group group = groupFindService.findGroup(groupId);
        Member member = memberFindService.findMember(hostId);

        group.closeEarly(member);
    }

    @Transactional
    public void delete(Long hostId, Long groupId) {
        Group group = groupFindService.findGroup(groupId);
        Member member = memberFindService.findMember(hostId);
        group.validateGroupIsInitialState(member);

        groupRepository.deleteById(groupId);
    }
}
