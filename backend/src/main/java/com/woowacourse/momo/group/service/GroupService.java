package com.woowacourse.momo.group.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.group.domain.duration.Duration;
import com.woowacourse.momo.group.domain.group.Group;
import com.woowacourse.momo.group.domain.group.GroupRepository;
import com.woowacourse.momo.group.domain.schedule.Schedule;
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

    private static final int DEFAULT_PAGE_SIZE = 12;
    private final MemberFindService memberFindService;
    private final GroupFindService groupFindService;
    private final GroupRepository groupRepository;

    @Transactional
    public GroupIdResponse create(Long memberId, GroupRequest groupRequest) {
        Member host = memberFindService.findMember(memberId);
        Group group = groupRepository.save(GroupRequestAssembler.group(host, groupRequest));

        return GroupResponseAssembler.groupIdResponse(group);
    }

    public GroupResponse findById(Long id) {
        Group group = groupFindService.findGroup(id);
        return GroupResponseAssembler.groupResponse(group);
    }

    public List<GroupSummaryResponse> findAll() {
        List<Group> groups = groupFindService.findGroups();

        return GroupResponseAssembler.groupSummaryResponses(groups);
    }

    public GroupPageResponse findAll(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, DEFAULT_PAGE_SIZE);
        Page<Group> groups = groupFindService.findGroups(pageable);
        List<Group> groupsOfPage = groups.getContent();
        List<GroupSummaryResponse> summaries = GroupResponseAssembler.groupSummaryResponses(groupsOfPage);

        return GroupResponseAssembler.groupPageResponse(summaries, groups.hasNext(), pageNumber);
    }

    @Transactional
    public void update(Long hostId, Long groupId, GroupUpdateRequest request) {
        Group group = groupFindService.findGroup(groupId);
        validateHost(group, hostId);
        validateFinishedRecruitment(group);

        List<Schedule> schedules = GroupRequestAssembler.schedules(request.getSchedules());
        Duration duration = GroupRequestAssembler.duration(request.getDuration());
        validateSchedulesInDuration(schedules, duration);

        group.update(Category.from(request.getCategoryId()), request.getCapacity(),
                duration, request.getDeadline(), schedules,
                request.getLocation(), request.getDescription());
    }

    private void validateSchedulesInDuration(List<Schedule> schedules, Duration duration) {
        // 일정의 일자가 모임 기간에 포함되지 않습니다.
        schedules.stream()
                .filter(schedule -> !schedule.checkInRange(duration.getStartDate(), duration.getEndDate()))
                .findAny()
                .ifPresent(schedule -> { throw new IllegalArgumentException("GROUP_ERROR_004"); });
    }

    @Transactional
    public void closeEarly(Long hostId, Long groupId) {
        Group group = groupFindService.findGroup(groupId);
        validateHost(group, hostId);
        validateFinishedRecruitment(group);

        group.closeEarly();
    }

    @Transactional
    public void delete(Long hostId, Long groupId) {
        Group group = groupFindService.findGroup(groupId);
        validateHost(group, hostId);
        validateFinishedRecruitment(group);

        groupRepository.deleteById(groupId);
    }

    private void validateHost(Group group, Long hostId) {
        Member host = memberFindService.findMember(hostId);
        if (!group.isSameHost(host)) {
            throw new IllegalArgumentException("AUTH_ERROR_004"); // 해당 모임의 주최자가 아닙니다.
        }
    }

    private void validateFinishedRecruitment(Group group) {
        if (group.isFinishedRecruitment()) {
            throw new IllegalArgumentException("GROUP_ERROR_006"); // 모집 마감된 모임은 수정 및 삭제할 수 없습니다.
        }
    }

    public List<GroupSummaryResponse> findGroupOfMember(Long memberId) {
        List<Group> participatedGroups = groupFindService.findRelatedGroups(memberId);

        return GroupResponseAssembler.groupSummaryResponses(participatedGroups);
    }
}
