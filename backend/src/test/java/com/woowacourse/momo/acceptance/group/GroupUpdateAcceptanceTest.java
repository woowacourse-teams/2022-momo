package com.woowacourse.momo.acceptance.group;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;

import static com.woowacourse.momo.acceptance.group.GroupRestHandler.모임을_수정한다;
import static com.woowacourse.momo.fixture.GroupFixture.DUDU_STUDY;
import static com.woowacourse.momo.fixture.MemberFixture.DUDU;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import io.restassured.response.ValidatableResponse;

import com.woowacourse.momo.acceptance.AcceptanceTest;
import com.woowacourse.momo.fixture.GroupFixture;
import com.woowacourse.momo.fixture.MemberFixture;
import com.woowacourse.momo.group.service.dto.response.ScheduleResponse;

class GroupUpdateAcceptanceTest extends AcceptanceTest {

    private static final GroupFixture GROUP = GroupFixture.MOMO_STUDY;
    private static final MemberFixture HOST = MemberFixture.MOMO;

    private String hostAccessToken;
    private Long groupId;

    @BeforeEach
    void setUp() {
        hostAccessToken = HOST.로_로그인한다();
        groupId = GROUP.을_생성한다(hostAccessToken);
    }

    @DisplayName("주최자가 모임을 수정한다")
    @Test
    void updateGroupByHost() {
        GroupFixture updatedGroup = DUDU_STUDY;
        모임을_수정한다(hostAccessToken, groupId, updatedGroup).statusCode(HttpStatus.OK.value()); // TODO: NO_CONTENT

        ValidatableResponse response = GroupRestHandler.모임을_조회한다(groupId)
                .statusCode(HttpStatus.OK.value());

        assertAll(
                () -> {
                    String startDuration = updatedGroup.getDuration().getStartDate().format(DateTimeFormatter.ISO_DATE);
                    String endDuration = updatedGroup.getDuration().getEndDate().format(DateTimeFormatter.ISO_DATE);
                    String deadline = updatedGroup.getDeadline()
                            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
                    response
                            .body("name", is(updatedGroup.getName()))
                            .body("host.id", is(1))
                            .body("host.name", is(HOST.getName()))
                            .body("categoryId", is(updatedGroup.getCategoryId().intValue()))
                            .body("capacity", is(updatedGroup.getCapacity()))
                            .body("duration.start", is(startDuration))
                            .body("duration.end", is(endDuration))
                            .body("deadline", is(deadline))
                            .body("location", is(updatedGroup.getLocation()))
                            .body("description", is(updatedGroup.getDescription()));
                },
                () -> {
                    List<ScheduleResponse> schedules = response.extract()
                            .jsonPath()
                            .getList("schedules", ScheduleResponse.class);

                    assertThat(schedules).usingRecursiveComparison()
                            .isEqualTo(updatedGroup.getSchedules());
                }
        );
    }

    @DisplayName("주최자가 아닌 회원이 모임을 수정한다")
    @Test
    void updateGroupByAnotherMember() {
        String anotherAccessToken = DUDU.로_로그인한다();
        모임을_수정한다(anotherAccessToken, groupId, DUDU_STUDY).statusCode(
                HttpStatus.BAD_REQUEST.value()); // TODO: UNAUTHORIZED
    }

    @DisplayName("비회원이 모임을 수정한다")
    @Test
    void updateGroupByNonMember() {
        모임을_수정한다(groupId, DUDU_STUDY).statusCode(HttpStatus.BAD_REQUEST.value()); // TODO: UNAUTHORIZED
    }

    @DisplayName("존재하지 않은 모임을 삭제한다")
    @Test
    void updateNonExistentGroup() {
        모임을_수정한다(hostAccessToken, 0L, DUDU_STUDY).statusCode(HttpStatus.BAD_REQUEST.value()); // TODO: NOT_FOUND
    }
}
