package com.woowacourse.momo.acceptance.group;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;

import static com.woowacourse.momo.acceptance.favorite.FavoriteRestHandler.모임을_찜한다;
import static com.woowacourse.momo.acceptance.group.GroupRestHandler.모임을_조회한다;
import static com.woowacourse.momo.acceptance.group.GroupRestHandler.본인이_주최한_모임을_조회한다;
import static com.woowacourse.momo.acceptance.group.GroupRestHandler.본인이_찜한_모임을_조회한다;
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

import org.hamcrest.Matchers;
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
import com.woowacourse.momo.fixture.calendar.ScheduleFixture;
import com.woowacourse.momo.group.service.dto.response.ScheduleResponse;
import com.woowacourse.momo.member.service.dto.response.MemberResponse;

@SuppressWarnings("NonAsciiCharacters")
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

    @DisplayName("회원이 찜한 모임을 조회한다")
    @Test
    void findLikeGroupByMember() {
        GroupFixture group = MOMO_STUDY;
        모임을_찜한다(hostAccessToken, groupIds.get(group));

        ValidatableResponse response = 모임을_조회한다(hostAccessToken, groupIds.get(group));

        checkLikedGroupResponse(response, group);
    }

    @DisplayName("비회원이 모임을 조회한다")
    @Test
    void findGroupByNonMember() {
        GroupFixture group = MOMO_STUDY;
        ValidatableResponse response = 모임을_조회한다(groupIds.get(group));

        checkGroupResponse(response, group);
    }

    void checkLikedGroupResponse(ValidatableResponse response, GroupFixture group) {
        checkGroupResponseWithLikeOrNot(response, group, true);
    }

    void checkGroupResponse(ValidatableResponse response, GroupFixture group) {
        checkGroupResponseWithLikeOrNot(response, group, false);
    }

    void checkGroupResponseWithLikeOrNot(ValidatableResponse response, GroupFixture group, boolean like) {
        response.statusCode(HttpStatus.OK.value());

        assertAll(
                () -> {
                    String startDuration = group.getDuration().getStartDate().format(DateTimeFormatter.ISO_DATE);
                    String endDuration = group.getDuration().getEndDate().format(DateTimeFormatter.ISO_DATE);
                    String deadline = group.getDeadline().getValue()
                            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
                    response
                            .body("name", is(group.getName().getValue()))
                            .body("host.id", is(1))
                            .body("host.name", is(HOST.getName()))
                            .body("categoryId", is((int) group.getCategoryId()))
                            .body("capacity", is(group.getCapacity().getValue()))
                            .body("duration.start", is(startDuration))
                            .body("duration.end", is(endDuration))
                            .body("finished", is(false))
                            .body("deadline", is(deadline))
                            .body("location.address", is(group.getLocation().getAddress()))
                            .body("location.buildingName", is(group.getLocation().getBuildingName()))
                            .body("location.detail", is(group.getLocation().getDetail()))
                            .body("description", is(group.getDescription().getValue()))
                            .body("like", is(like));
                },
                () -> {
                    List<ScheduleResponse> schedules = response.extract()
                            .jsonPath()
                            .getList("schedules", ScheduleResponse.class);

                    assertThat(schedules).hasSize(group.getSchedules().size());

                    for (int i = 0; i < schedules.size(); i++) {
                        ScheduleResponse actual = schedules.get(i);
                        ScheduleFixture expected = group.getSchedules().get(i);
                        assertAll(
                                () -> assertThat(actual.getDate()).isEqualTo(expected.getDate()),
                                () -> assertThat(actual.getStartTime()).isEqualTo(expected.getStartTime()),
                                () -> assertThat(actual.getEndTime()).isEqualTo(expected.getEndTime())
                        );
                    }
                }
        );
    }

    @DisplayName("존재하지 않은 모임을 조회한다")
    @Test
    void findNonExistentGroup() {
        모임을_조회한다(hostAccessToken, 0L)
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("message", Matchers.is("GROUP_001"));
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
    void findParticipatedGroups() {
        String anotherHostAccessToken = MemberFixture.DUDU.로_로그인한다();
        DUDU_STUDY.을_생성한다(anotherHostAccessToken);

        ValidatableResponse response = 본인이_참여한_모임을_조회한다(anotherHostAccessToken);

        checkParticipatedGroup(response);
    }

    @DisplayName("본인이 주최하고 있는 모임들을 조회한다.")
    @Test
    void findHostedGroups() {
        hostAccessToken = HOST.로_로그인한다();

        ValidatableResponse response = 본인이_주최한_모임을_조회한다(hostAccessToken);

        checkHostedGroup(response);
    }

    @DisplayName("본인이 찜한 모임들을 조회한다.")
    @Test
    void findLikedGroups() {
        hostAccessToken = HOST.로_로그인한다();
        모임을_찜한다(hostAccessToken, groupIds.get(MOMO_TRAVEL));
        모임을_찜한다(hostAccessToken, groupIds.get(DUDU_STUDY));

        ValidatableResponse response = 본인이_찜한_모임을_조회한다(hostAccessToken);

        checkLikedGroup(response);
    }

    private void checkParticipatedGroup(ValidatableResponse response) {
        response.statusCode(HttpStatus.OK.value());
        response.body("groups", hasSize(1));
    }

    private void checkHostedGroup(ValidatableResponse response) {
        response.statusCode(HttpStatus.OK.value());
        response.body("groups", hasSize(4));
    }

    private void checkLikedGroup(ValidatableResponse response) {
        response.statusCode(HttpStatus.OK.value());
        response.body("groups", hasSize(2));
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
                .filter(g -> g.getCategoryId() == category.getId())
                .collect(Collectors.toList());

        checkGroupSummaryResponses(response, groups);
    }

    private void checkGroupSummaryResponsesByKeyword(String keyword, ValidatableResponse response) {
        List<GroupFixture> groups = groupIds.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .map(Entry::getKey)
                .filter(g -> g.getName().getValue().contains(keyword))
                .collect(Collectors.toList());

        checkGroupSummaryResponses(response, groups);
    }

    private void checkGroupSummaryResponses(ValidatableResponse response, List<GroupFixture> groups) {
        response.statusCode(HttpStatus.OK.value());

        for (int i = 0; i < groups.size(); i++) {
            GroupFixture group = groups.get(i);
            String deadline = group.getDeadline().getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));

            int numOfParticipant = ParticipantRestHandler.참여목록을_조회한다(groupIds.get(group))
                    .extract()
                    .as(MemberResponse[].class)
                    .length;

            String index = String.format("groups[%d]", i);
            response.body(index + ".id", is(groupIds.get(group).intValue()))
                    .body(index + ".name", is(group.getName().getValue()))
                    .body(index + ".categoryId", is((int) group.getCategoryId()))
                    .body(index + ".capacity", is(group.getCapacity().getValue()))
                    .body(index + ".numOfParticipant", is(numOfParticipant))
                    .body(index + ".finished", is(false))
                    .body(index + ".deadline", is(deadline))
                    .body(index + ".host.id", is(1))
                    .body(index + ".host.name", is(HOST.getName()));
        }
    }
}
