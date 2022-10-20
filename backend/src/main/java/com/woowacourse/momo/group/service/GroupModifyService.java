package com.woowacourse.momo.group.service;

import static com.woowacourse.momo.group.exception.GroupErrorCode.MEMBER_IS_NOT_HOST;
import static com.woowacourse.momo.group.exception.GroupErrorCode.SCHEDULE_MUST_BE_INCLUDED_IN_DURATION;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.group.domain.Description;
import com.woowacourse.momo.group.domain.Group;
import com.woowacourse.momo.group.domain.GroupName;
import com.woowacourse.momo.group.domain.GroupRepository;
import com.woowacourse.momo.group.domain.Location;
import com.woowacourse.momo.group.domain.calendar.Calendar;
import com.woowacourse.momo.group.domain.calendar.Duration;
import com.woowacourse.momo.group.domain.calendar.Schedule;
import com.woowacourse.momo.group.domain.calendar.ScheduleRepository;
import com.woowacourse.momo.group.domain.participant.Capacity;
import com.woowacourse.momo.group.domain.participant.ParticipantRepository;
import com.woowacourse.momo.group.event.GroupCreateEvent;
import com.woowacourse.momo.group.event.GroupDeleteEvent;
import com.woowacourse.momo.group.exception.GroupException;
import com.woowacourse.momo.group.service.dto.request.GroupRequest;
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
    private final ScheduleRepository scheduleRepository;
    private final ParticipantRepository participantRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public GroupIdResponse create(Long memberId, GroupRequest request) {
        Member host = memberFindService.findMember(memberId);
        validateSchedulesAreInDuration(request.getSchedules(), request.getDuration());

        Group group = new Group(host, request.getCapacity(), request.getCalendar(), request.getName(),
                request.getCategory(), request.getLocation(), request.getDescription());
        Group savedGroup = groupRepository.save(group);
        saveSchedules(request, savedGroup);

        applicationEventPublisher.publishEvent(new GroupCreateEvent(group.getId(), group.getCategory()));

        return GroupResponseAssembler.groupIdResponse(savedGroup);
    }

    private void saveSchedules(GroupRequest request, Group group) {
        List<Schedule> schedules = request.getSchedules();
        for (Schedule schedule : schedules) {
            group.addSchedule(schedule);
        }
    }

    private void validateSchedulesAreInDuration(List<Schedule> schedules, Duration duration) {
        boolean hasOutOfDuration = schedules.stream()
                .anyMatch(schedule -> schedule.isOutOfDuration(duration));

        if (hasOutOfDuration) {
            throw new GroupException(SCHEDULE_MUST_BE_INCLUDED_IN_DURATION);
        }
    }

    @Transactional
    public void update(Long hostId, Long groupId, GroupRequest request) {
        ifMemberIsHost(hostId, groupId, (host, group) -> {
            updateGroup(group, request);
            updateSchedules(group, request.getSchedules());
        });
    }

    private void updateGroup(Group group, GroupRequest request) {
        GroupName groupName = request.getName();
        Category category = request.getCategory();
        Capacity capacity = request.getCapacity();
        Calendar calendar = request.getCalendar();
        Location location = request.getLocation();
        Description description = request.getDescription();

        group.update(capacity, calendar, groupName, category, location, description);
    }

    private void updateSchedules(Group group, List<Schedule> newSchedules) {
        List<Schedule> presentSchedules = group.getSchedules();
        List<Schedule> toBeDeletedSchedules = presentSchedules.stream()
                .filter(schedule -> notContainSchedules(newSchedules, schedule))
                .collect(Collectors.toList());

        List<Schedule> toBeSavedSchedules = newSchedules.stream()
                .filter(schedule -> notContainSchedules(presentSchedules, schedule))
                .collect(Collectors.toList());

        reflectSchedules(group, toBeDeletedSchedules, toBeSavedSchedules);
    }

    private boolean notContainSchedules(List<Schedule> schedules, Schedule schedule) {
        return schedules.stream()
                .noneMatch(schedule::equalsDateTime);
    }

    private void reflectSchedules(Group group, List<Schedule> toBeDeletedSchedules, List<Schedule> toBeSavedIds) {
        if (!toBeDeletedSchedules.isEmpty()) {
            scheduleRepository.deleteAllInSchedules(toBeDeletedSchedules);
        }

        for (Schedule schedule : toBeSavedIds) {
            group.addSchedule(schedule);
        }
    }

    @Transactional
    public void closeEarly(Long hostId, Long groupId) {
        ifMemberIsHost(hostId, groupId, (host, group) -> group.closeEarly());
    }

    @Transactional
    public void delete(Long hostId, Long groupId) {
        ifMemberIsHost(hostId, groupId, (host, group) -> {
            group.validateGroupIsProceeding();
            applicationEventPublisher.publishEvent(new GroupDeleteEvent(groupId));
            scheduleRepository.deleteAllByGroupId(groupId);
            participantRepository.deleteAllByGroupId(groupId);
            groupRepository.deleteById(groupId);
        });
    }

    private void ifMemberIsHost(Long hostId, Long groupId, BiConsumer<Member, Group> consumer) {
        Member host = memberFindService.findMember(hostId);
        Group group = groupFindService.findGroup(groupId);

        validateMemberIsHost(group, host);

        consumer.accept(host, group);
    }

    private void validateMemberIsHost(Group group, Member member) {
        if (group.isNotHost(member)) {
            throw new GroupException(MEMBER_IS_NOT_HOST);
        }
    }
}
