package com.woowacourse.momo.acceptance.group;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;

import static com.woowacourse.momo.acceptance.group.GroupRestHandler.모임을_조회한다;
import static com.woowacourse.momo.acceptance.group.GroupRestHandler.본인이_참여한_모임을_조회한다;
import static com.woowacourse.momo.acceptance.group.GroupRestHandler.카테고리별_모임목록을_조회한다;
import static com.woowacourse.momo.acceptance.group.GroupRestHandler.키워드로_모임목록을_조회한다;
import static com.woowacourse.momo.acceptance.group.GroupRestHandler.페이지로_모임목록을_조회한다;
import static com.woowacourse.momo.fixture.GroupFixture.DUDU_COFFEE_TIME;
import static com.woowacourse.momo.fixture.GroupFixture.DUDU_STUDY;
import static com.woowacourse.momo.fixture.GroupFixture.MOMO_STUDY;
import static com.woowacourse.momo.fixture.GroupFixture.MOMO_TRAVEL;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import io.restassured.response.ValidatableResponse;

import com.woowacourse.momo.acceptance.AcceptanceTest;
import com.woowacourse.momo.acceptance.participant.ParticipantRestHandler;
import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.fixture.GroupFixture;
import com.woowacourse.momo.fixture.MemberFixture;
import com.woowacourse.momo.group.service.dto.response.ScheduleResponse;
import com.woowacourse.momo.member.service.dto.response.MemberResponse;

class GroupFindAcceptanceTest extends AcceptanceTest {

    private static final MemberFixture HOST = MemberFixture.MOMO;
    private static final int FIRST_PAGE_NUMBER = 0;

    private String hostAccessToken;
    private Map<GroupFixture, Long> groupIds;

    @BeforeEach
    void setUp() {
        hostAccessToken = HOST.로_로그인한다();
        groupIds = Stream.of(MOMO_STUDY, MOMO_TRAVEL, DUDU_STUDY, DUDU_COFFEE_TIME)
                .collect(Collectors.toMap(
                        group -> group,
                        group -> group.을_생성한다(hostAccessToken)
                ));
    }

    @DisplayName("회원이 모임을 조회한다")
    @Test
    void findGroupByMember() {
        GroupFixture group = MOMO_STUDY;
        ValidatableResponse response = 모임을_조회한다(hostAccessToken, groupIds.get(group));

        checkGroupResponse(response, group);
    }

    @DisplayName("비회원이 모임을 조회한다")
    @Test
    void findGroupByNonMember() {
        GroupFixture group = MOMO_STUDY;
        ValidatableResponse response = 모임을_조회한다(groupIds.get(group));

        checkGroupResponse(response, group);
    }

    void checkGroupResponse(ValidatableResponse response, GroupFixture group) {
        response.statusCode(HttpStatus.OK.value());

        assertAll(
                () -> {
                    String startDuration = group.getDuration().getStartDate().format(DateTimeFormatter.ISO_DATE);
                    String endDuration = group.getDuration().getEndDate().format(DateTimeFormatter.ISO_DATE);
                    String deadline = group.getDeadline().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
                    response
                            .body("name", is(group.getName()))
                            .body("host.id", is(1))
                            .body("host.name", is(HOST.getName()))
                            .body("categoryId", is(group.getCategoryId().intValue()))
                            .body("capacity", is(group.getCapacity()))
                            .body("duration.start", is(startDuration))
                            .body("duration.end", is(endDuration))
                            .body("finished", is(false))
                            .body("deadline", is(deadline))
                            .body("location", is(group.getLocation()))
                            .body("description", is(group.getDescription()));
                },
                () -> {
                    List<ScheduleResponse> schedules = response.extract()
                            .jsonPath()
                            .getList("schedules", ScheduleResponse.class);

                    assertThat(schedules).usingRecursiveComparison()
                            .isEqualTo(group.getSchedules());
                }
        );
    }

    @DisplayName("존재하지 않은 모임을 조회한다")
    @Test
    void findNonExistentGroup() {
        모임을_조회한다(hostAccessToken, 0L).statusCode(HttpStatus.BAD_REQUEST.value()); // TOD: NOT_FOUND
    }

    @DisplayName("모임목록중 첫번째 페이지를 조회한다")
    @Test
    void findGroupsByPageNumber() {
        ValidatableResponse response = 페이지로_모임목록을_조회한다(FIRST_PAGE_NUMBER);

        checkGroupSummaryResponsesByPageNumber(response);
    }

    @DisplayName("카테고리별 모임 목록을 조회한다")
    @Test
    void findGroupsByCategoryAndPageNumber() {
        ValidatableResponse response = 카테고리별_모임목록을_조회한다(Category.STUDY, FIRST_PAGE_NUMBER);

        checkGroupSummaryResponsesByCategory(response, Category.STUDY);
    }

    @DisplayName("키워드로 모임 목록을 조회한다")
    @Test
    void findGroupsByKeywordAndPageNumber() {
        String keyword = "모모";
        ValidatableResponse response = 키워드로_모임목록을_조회한다(keyword, FIRST_PAGE_NUMBER);

        checkGroupSummaryResponsesByKeyword(keyword, response);
    }

    @DisplayName("본인이 참여하고 있는 모임들을 조회한다.")
    @Test
    void findGroupsParticipated() {
        String anotherHostAccessToken = MemberFixture.DUDU.로_로그인한다();
        DUDU_STUDY.을_생성한다(anotherHostAccessToken);

        ValidatableResponse response = 본인이_참여한_모임을_조회한다(anotherHostAccessToken);

        checkGroupParticipated(response);
    }

    private void checkGroupParticipated(ValidatableResponse response) {
        response.statusCode(HttpStatus.OK.value());
        response.body("groups", hasSize(1));
    }

    private void checkGroupSummaryResponsesByPageNumber(ValidatableResponse response) {
        List<GroupFixture> groups = groupIds.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .map(Entry::getKey)
                .collect(Collectors.toList());

        checkGroupSummaryResponses(response, groups);
    }

    private void checkGroupSummaryResponsesByCategory(ValidatableResponse response, Category category) {
        List<GroupFixture> groups = groupIds.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .map(Entry::getKey)
                .filter(g -> g.getCategoryId().equals(category.getId()))
                .collect(Collectors.toList());

        checkGroupSummaryResponses(response, groups);
    }

    private void checkGroupSummaryResponsesByKeyword(String keyword, ValidatableResponse response) {
        List<GroupFixture> groups = groupIds.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .map(Entry::getKey)
                .filter(g -> g.getName().contains(keyword))
                .collect(Collectors.toList());

        checkGroupSummaryResponses(response, groups);
    }

    private void checkGroupSummaryResponses(ValidatableResponse response, List<GroupFixture> groups) {
        response.statusCode(HttpStatus.OK.value());

        for (int i = 0; i < groups.size(); i++) {
            GroupFixture group = groups.get(i);
            String deadline = group.getDeadline().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));

            int numOfParticipant = ParticipantRestHandler.참여목록을_조회한다(groupIds.get(group))
                    .extract()
                    .as(MemberResponse[].class)
                    .length;

            String index = String.format("groups[%d]", i);
            response.body(index + ".id", is(groupIds.get(group).intValue()))
                    .body(index + ".name", is(group.getName()))
                    .body(index + ".categoryId", is(group.getCategoryId().intValue()))
                    .body(index + ".capacity", is(group.getCapacity()))
                    .body(index + ".numOfParticipant", is(numOfParticipant))
                    .body(index + ".finished", is(false))
                    .body(index + ".deadline", is(deadline))
                    .body(index + ".host.id", is(1))
                    .body(index + ".host.name", is(HOST.getName()));
        }
    }
}
