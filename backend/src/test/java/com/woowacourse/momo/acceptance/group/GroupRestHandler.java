package com.woowacourse.momo.acceptance.group;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import io.restassured.response.ValidatableResponse;

import com.woowacourse.momo.acceptance.RestHandler;
import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.fixture.GroupFixture;
import com.woowacourse.momo.group.domain.duration.Duration;
import com.woowacourse.momo.group.domain.schedule.Schedule;
import com.woowacourse.momo.group.service.dto.request.DurationRequest;
import com.woowacourse.momo.group.service.dto.request.GroupRequest;
import com.woowacourse.momo.group.service.dto.request.GroupUpdateRequest;
import com.woowacourse.momo.group.service.dto.request.ScheduleRequest;

@SuppressWarnings("NonAsciiCharacters")
public class GroupRestHandler extends RestHandler {

    private static final String BASE_URL = "/api/groups";

    public static ValidatableResponse 모임을_생성한다(String accessToken, GroupFixture group) {
        GroupRequest request = groupRequest(group);
        return postRequest(accessToken, request, BASE_URL);
    }

    public static ValidatableResponse 모임을_생성한다(GroupFixture group) {
        GroupRequest request = groupRequest(group);
        return postRequest(request, BASE_URL);
    }

    public static ValidatableResponse 모임을_조회한다(String accessToken, Long groupId) {
        return getRequest(accessToken, BASE_URL + "/" + groupId);
    }

    public static ValidatableResponse 모임을_조회한다(Long groupId) {
        return getRequest(BASE_URL + "/" + groupId);
    }

    public static ValidatableResponse 모임을_수정한다(String accessToken, Long groupId, GroupFixture group) {
        GroupUpdateRequest request = groupUpdateRequest(group);
        return putRequest(accessToken, request, BASE_URL + "/" + groupId);
    }

    public static ValidatableResponse 본인이_참여한_모임을_조회한다(String accessToken) {
        return getRequest(accessToken, BASE_URL + "/me/participated");
    }

    public static ValidatableResponse 본인이_주최한_모임을_조회한다(String accessToken) {
        return getRequest(accessToken, BASE_URL + "/me/hosted");
    }

    public static ValidatableResponse 페이지로_모임목록을_조회한다(int pageNumber) {
        return getRequest(BASE_URL + "?page=" + pageNumber);
    }

    public static ValidatableResponse 페이지로_모임목록을_조회한다() {
        return getRequest(BASE_URL + "?page=0");
    }

    public static ValidatableResponse 카테고리별_모임목록을_조회한다(Category category, int firstPageNumber) {
        return getRequest(BASE_URL + "?category=" + category.getId() + "&page=" + firstPageNumber);
    }

    public static ValidatableResponse 키워드로_모임목록을_조회한다(String keyword, int firstPageNumber) {
        return getRequest(BASE_URL + "?keyword=" + keyword + "&page=" + firstPageNumber);
    }

    public static ValidatableResponse 모임을_수정한다(Long groupId, GroupFixture group) {
        GroupUpdateRequest request = groupUpdateRequest(group);
        return putRequest(request, BASE_URL + "/" + groupId);
    }

    public static ValidatableResponse 모임을_조기마감한다(String accessToken, Long groupId) {
        return postRequestWithNoBody(accessToken, BASE_URL + "/" + groupId + "/close");
    }

    public static ValidatableResponse 모임을_삭제한다(String accessToken, Long groupId) {
        return deleteRequest(accessToken, BASE_URL + "/" + groupId);
    }

    public static ValidatableResponse 모임을_삭제한다(Long groupId) {
        return deleteRequest(BASE_URL + "/" + groupId);
    }


    public static GroupRequest groupRequest(GroupFixture group) {
        return groupRequest(group.getName(), group.getCategoryId(), group.getCapacity(), group.getDuration(),
                group.getSchedules(), group.getDeadline(), group.getLocation(), group.getDescription());
    }

    public static GroupRequest groupRequest(String name, Long categoryId, int capacity, Duration duration,
                                            List<Schedule> schedules, LocalDateTime deadline, String location,
                                            String description) {
        DurationRequest durationRequest = durationRequest(duration);
        List<ScheduleRequest> scheduleRequests = scheduleRequests(schedules);
        return new GroupRequest(name, categoryId, capacity, durationRequest, scheduleRequests, deadline, location,
                description);
    }

    public static GroupUpdateRequest groupUpdateRequest(GroupFixture group) {
        return groupUpdateRequest(group.getName(), group.getCategoryId(), group.getCapacity(), group.getDuration(),
                group.getSchedules(),
                group.getDeadline(), group.getLocation(), group.getDescription());
    }

    public static GroupUpdateRequest groupUpdateRequest(String name, Long categoryId, Integer capacity,
                                                        Duration duration,
                                                        List<Schedule> schedules, LocalDateTime deadline,
                                                        String location, String description) {
        DurationRequest durationRequest = durationRequest(duration);
        List<ScheduleRequest> scheduleRequests = scheduleRequests(schedules);
        return new GroupUpdateRequest(name, categoryId, capacity, durationRequest, scheduleRequests, deadline, location,
                description);
    }

    private static DurationRequest durationRequest(Duration duration) {
        return new DurationRequest(duration.getStartDate(), duration.getEndDate());
    }

    private static List<ScheduleRequest> scheduleRequests(List<Schedule> schedules) {
        return schedules.stream()
                .map(GroupRestHandler::scheduleRequest)
                .collect(Collectors.toList());
    }

    private static ScheduleRequest scheduleRequest(Schedule schedule) {
        return new ScheduleRequest(schedule.getDate(), schedule.getStartTime(), schedule.getEndTime());
    }
}
