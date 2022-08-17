package com.woowacourse.momo.group.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.global.exception.exception.ErrorCode;
import com.woowacourse.momo.global.exception.exception.MomoException;
import com.woowacourse.momo.group.domain.calendar.Duration;
import com.woowacourse.momo.group.domain.calendar.Schedule;
import com.woowacourse.momo.group.domain.group.Group;
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

        List<Schedule> schedules = GroupRequestAssembler.schedules(request.getSchedules());
        Duration duration = GroupRequestAssembler.duration(request.getDuration());
        validateSchedulesInDuration(schedules, duration);

        group.update(request.getName(), host, Category.from(request.getCategoryId()), request.getCapacity(),
                duration, request.getDeadline(), schedules,
                request.getLocation(), request.getDescription());
    }

    private void validateSchedulesInDuration(List<Schedule> schedules, Duration duration) {
        if (existAnyScheduleOutOfDuration(schedules, duration)) {
            throw new MomoException(ErrorCode.GROUP_SCHEDULE_NOT_RANGE_DURATION);
        }
    }

    private boolean existAnyScheduleOutOfDuration(List<Schedule> schedules, Duration duration) {
        return schedules.stream()
                .anyMatch(schedule -> !schedule.checkInRange(duration.getStartDate(), duration.getEndDate()));
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
