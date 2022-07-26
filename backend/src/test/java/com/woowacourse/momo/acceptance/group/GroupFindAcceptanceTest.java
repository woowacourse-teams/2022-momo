package com.woowacourse.momo.acceptance.group;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;

import static com.woowacourse.momo.acceptance.group.GroupRestHandler.모임목록을_조회한다;
import static com.woowacourse.momo.acceptance.group.GroupRestHandler.모임을_조회한다;
import static com.woowacourse.momo.fixture.GroupFixture.DUDU_STUDY;
import static com.woowacourse.momo.fixture.GroupFixture.MOMO_STUDY;
import static com.woowacourse.momo.fixture.GroupFixture.MOMO_TRAVEL;

import java.time.format.DateTimeFormatter;
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
import com.woowacourse.momo.fixture.GroupFixture;
import com.woowacourse.momo.fixture.MemberFixture;
import com.woowacourse.momo.group.service.dto.response.ScheduleResponse;

class GroupFindAcceptanceTest extends AcceptanceTest {

    private static final MemberFixture HOST = MemberFixture.MOMO;

    private String hostAccessToken;
    private Map<GroupFixture, Long> groupIds;

    @BeforeEach
    void setUp() {
        hostAccessToken = HOST.로_로그인하다();
        groupIds = Stream.of(MOMO_STUDY, MOMO_TRAVEL, DUDU_STUDY)
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

        checkGroupDetailResponse(response, group);
    }

    @DisplayName("비회원이 모임을 조회한다")
    @Test
    void findGroupByNonMember() {
        GroupFixture group = MOMO_STUDY;
        ValidatableResponse response = 모임을_조회한다(groupIds.get(group));

        checkGroupDetailResponse(response, group);
    }

    void checkGroupDetailResponse(ValidatableResponse response, GroupFixture group) {
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
                            .body("duration.start", is(startDuration))
                            .body("duration.end", is(endDuration))
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

    @DisplayName("회원이 모임목록을 조회한다")
    @Test
    void findGroupsByMember() {
        ValidatableResponse response = 모임목록을_조회한다(hostAccessToken);

        checkGroupSimpleResponses(response);
    }

    @DisplayName("비회원이 모임목록을 조회한다")
    @Test
    void findGroupsByNonMember() {
        ValidatableResponse response = 모임목록을_조회한다();

        checkGroupSimpleResponses(response);
    }

    void checkGroupSimpleResponses(ValidatableResponse response) {
        response.statusCode(HttpStatus.OK.value());

        List<GroupFixture> groups = groupIds.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Entry::getKey)
                .collect(Collectors.toList());

        for (int i = 0; i < groups.size(); i++) {
            GroupFixture group = groups.get(i);
            String deadline = group.getDeadline().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));

            String index = String.format("[%d]", i);
            response.body(index + ".id", is(groupIds.get(group).intValue()))
                    .body(index + ".name", is(group.getName()))
                    .body(index + ".categoryId", is(group.getCategoryId().intValue()))
                    .body(index + ".deadline", is(deadline))
                    .body(index + ".host.id", is(1))
                    .body(index + ".host.name", is(HOST.getName()));
        }
    }
}
