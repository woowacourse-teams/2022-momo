package com.woowacourse.momo.group.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.global.exception.exception.ErrorCode;
import com.woowacourse.momo.global.exception.exception.MomoException;
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

    public GroupPageResponse findAll(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, DEFAULT_PAGE_SIZE, Sort.by("id").descending());
        Page<Group> groups = groupFindService.findGroups(pageable);
        List<Group> groupsOfPage = groups.getContent();
        List<GroupSummaryResponse> summaries = GroupResponseAssembler.groupSummaryResponses(groupsOfPage);

        return GroupResponseAssembler.groupPageResponse(summaries, groups.hasNext(), pageNumber);
    }

    public GroupPageResponse findAllByCategory(String categoryName, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, DEFAULT_PAGE_SIZE, Sort.by("id").descending());
        Category category = Category.from(categoryName);
        Page<Group> groups = groupFindService.findGroupsByCategory(category, pageable);
        List<Group> groupsOfPage = groups.getContent();
        List<GroupSummaryResponse> summaries = GroupResponseAssembler.groupSummaryResponses(groupsOfPage);

        return GroupResponseAssembler.groupPageResponse(summaries, groups.hasNext(), pageNumber);
    }

    @Transactional
    public void update(Long hostId, Long groupId, GroupUpdateRequest request) {
        Group group = groupFindService.findGroup(groupId);
        validateInitialState(hostId, group);

        List<Schedule> schedules = GroupRequestAssembler.schedules(request.getSchedules());
        Duration duration = GroupRequestAssembler.duration(request.getDuration());
        validateSchedulesInDuration(schedules, duration);

        group.update(request.getName(), Category.from(request.getCategoryId()), request.getCapacity(),
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
        validateHost(group, hostId);
        validateFinishedRecruitment(group);

        group.closeEarly();
    }

    @Transactional
    public void delete(Long hostId, Long groupId) {
        Group group = groupFindService.findGroup(groupId);
        validateInitialState(hostId, group);

        groupRepository.deleteById(groupId);
    }

    private void validateInitialState(Long hostId, Group group) {
        validateHost(group, hostId);
        validateFinishedRecruitment(group);
        validateNotExistParticipants(group);
    }

    private void validateHost(Group group, Long hostId) {
        Member host = memberFindService.findMember(hostId);
        if (!group.isHost(host)) {
            throw new MomoException(ErrorCode.AUTH_DELETE_NO_HOST);
        }
    }

    private void validateNotExistParticipants(Group group) {
        if (group.isExistParticipants()) {
            throw new MomoException(ErrorCode.GROUP_EXIST_PARTICIPANTS);
        }
    }

    private void validateFinishedRecruitment(Group group) {
        if (group.isFinishedRecruitment()) {
            throw new MomoException(ErrorCode.GROUP_ALREADY_FINISH);
        }
    }

    public List<GroupSummaryResponse> findGroupOfMember(Long memberId) {
        List<Group> participatedGroups = groupFindService.findRelatedGroups(memberId);

        return GroupResponseAssembler.groupSummaryResponses(participatedGroups);
    }
}
