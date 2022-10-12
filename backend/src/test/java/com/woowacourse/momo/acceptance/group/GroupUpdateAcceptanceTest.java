package com.woowacourse.momo.acceptance.group;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;

import static com.woowacourse.momo.acceptance.group.GroupRestHandler.모임을_수정한다;
import static com.woowacourse.momo.acceptance.group.GroupRestHandler.모임을_조기마감한다;
import static com.woowacourse.momo.acceptance.group.GroupRestHandler.모임을_조회한다;
import static com.woowacourse.momo.acceptance.participant.ParticipantRestHandler.모임에_참여한다;
import static com.woowacourse.momo.fixture.GroupFixture.DUDU_STUDY;
import static com.woowacourse.momo.fixture.MemberFixture.DUDU;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import io.restassured.response.ValidatableResponse;

import com.woowacourse.momo.acceptance.AcceptanceTest;
import com.woowacourse.momo.fixture.GroupFixture;
import com.woowacourse.momo.fixture.MemberFixture;
import com.woowacourse.momo.fixture.calendar.ScheduleFixture;
import com.woowacourse.momo.group.service.dto.response.ScheduleResponse;

@SuppressWarnings("NonAsciiCharacters")
class GroupUpdateAcceptanceTest extends AcceptanceTest {

    private static final GroupFixture GROUP = GroupFixture.MOMO_STUDY;
    private static final MemberFixture HOST = MemberFixture.MOMO;
    private static final MemberFixture PARTICIPANT = MemberFixture.DUDU;

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
        모임을_수정한다(hostAccessToken, groupId, updatedGroup)
                .statusCode(HttpStatus.OK.value());

        ValidatableResponse response = 모임을_조회한다(groupId)
                .statusCode(HttpStatus.OK.value());

        assertAll(
                () -> {
                    String startDuration = updatedGroup.getDuration().getStartDate().format(DateTimeFormatter.ISO_DATE);
                    String endDuration = updatedGroup.getDuration().getEndDate().format(DateTimeFormatter.ISO_DATE);
                    String deadline = updatedGroup.getDeadline().getValue()
                            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
                    response
                            .body("host.id", is(1))
                            .body("host.name", is(HOST.getName()))
                            .body("categoryId", is((int) updatedGroup.getCategoryId()))
                            .body("capacity", is(updatedGroup.getCapacity().getValue()))
                            .body("duration.start", is(startDuration))
                            .body("duration.end", is(endDuration))
                            .body("deadline", is(deadline))
                            .body("location.address", is(updatedGroup.getLocation().getAddress()))
                            .body("location.buildingName", is(updatedGroup.getLocation().getBuildingName()))
                            .body("location.detail", is(updatedGroup.getLocation().getDetail()))
                            .body("description", is(updatedGroup.getDescription().getValue()));
                },
                () -> {
                    List<ScheduleResponse> schedules = response.extract()
                            .jsonPath()
                            .getList("schedules", ScheduleResponse.class);

                    assertThat(schedules).hasSize(updatedGroup.getSchedules().size());

                    for (int i = 0; i < schedules.size(); i++) {
                        ScheduleResponse actual = schedules.get(i);
                        ScheduleFixture expected = updatedGroup.getSchedules().get(i);
                        assertAll(
                                () -> assertThat(actual.getDate()).isEqualTo(expected.getDate()),
                                () -> assertThat(actual.getStartTime()).isEqualTo(expected.getStartTime()),
                                () -> assertThat(actual.getEndTime()).isEqualTo(expected.getEndTime())
                        );
                    }
                }
        );
    }

    @DisplayName("주최자가 아닌 회원이 모임을 수정한다")
    @Test
    void updateGroupByAnotherMember() {
        String anotherAccessToken = DUDU.로_로그인한다();

        모임을_수정한다(anotherAccessToken, groupId, DUDU_STUDY)
                .statusCode(HttpStatus.FORBIDDEN.value())
                .body("message", Matchers.is("GROUP_017"));
    }

    @DisplayName("비회원이 모임을 수정한다")
    @Test
    void updateGroupByNonMember() {
        모임을_수정한다(groupId, DUDU_STUDY)
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .body("message", Matchers.is("AUTH_003"));
    }

    @DisplayName("존재하지 않은 모임을 삭제한다")
    @Test
    void updateNonExistentGroup() {
        모임을_수정한다(hostAccessToken, 0L, DUDU_STUDY)
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("message", Matchers.is("GROUP_001"));
    }

    @DisplayName("모임을 조기 마감한다")
    @Test
    void closeEarly() {
        모임을_조기마감한다(hostAccessToken, groupId)
                .statusCode(HttpStatus.OK.value());

        모임을_조회한다(groupId)
                .statusCode(HttpStatus.OK.value())
                .body("finished", is(true));
    }

    @DisplayName("참여자가 있는 모임을 수정한다")
    @Test
    void updateExistParticipant() {
        String participantAccessToken = PARTICIPANT.로_로그인한다();

        모임에_참여한다(participantAccessToken, groupId);

        모임을_수정한다(hostAccessToken, groupId, DUDU_STUDY)
                .statusCode(HttpStatus.OK.value());
    }
}
