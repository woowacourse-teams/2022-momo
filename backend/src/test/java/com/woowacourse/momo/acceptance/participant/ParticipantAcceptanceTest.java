package com.woowacourse.momo.acceptance.participant;

import static com.woowacourse.momo.acceptance.group.GroupRestHandler.모임을_조기마감한다;
import static com.woowacourse.momo.acceptance.participant.ParticipantRestHandler.모임에_참여한다;
import static com.woowacourse.momo.acceptance.participant.ParticipantRestHandler.모임을_탈퇴한다;
import static com.woowacourse.momo.acceptance.participant.ParticipantRestHandler.참여목록을_조회한다;
import static com.woowacourse.momo.fixture.MemberFixture.GUGU;
import static com.woowacourse.momo.fixture.MemberFixture.MOMO;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.woowacourse.momo.acceptance.AcceptanceTest;
import com.woowacourse.momo.fixture.GroupFixture;
import com.woowacourse.momo.fixture.MemberFixture;

@SuppressWarnings("NonAsciiCharacters")
class ParticipantAcceptanceTest extends AcceptanceTest {

    private static final MemberFixture HOST = MemberFixture.DUDU;
    private static final GroupFixture GROUP = GroupFixture.DUDU_STUDY;
    private static final GroupFixture GROUP_CAPACITY_2 = GroupFixture.DUDU_COFFEE_TIME;

    private String hostAccessToken;
    private Long groupId;
    private Long groupCapacityTwoId;

    @BeforeEach
    void setUp() {
        hostAccessToken = HOST.로_로그인한다();
        groupId = GROUP.을_생성한다(hostAccessToken);
        groupCapacityTwoId = GROUP_CAPACITY_2.을_생성한다(hostAccessToken);
    }

    @DisplayName("회원이 모임에 참여한다")
    @Test
    void participateByMember() {
        String accessToken = MOMO.로_로그인한다();
        모임에_참여한다(accessToken, groupId)
                .statusCode(HttpStatus.OK.value());

        참여목록을_조회한다(groupId);
    }

    @DisplayName("주최자가 자신이 주최한 모임에 참여한다")
    @Test
    void participateByHost() {
        모임에_참여한다(hostAccessToken, groupId).statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("참여자가 자신이 참여한 모임에 재참여한다")
    @Test
    void participateByParticipants() {
        String accessToken = MOMO.로_로그인한다();

        모임에_참여한다(accessToken, groupId)
                .statusCode(HttpStatus.OK.value());

        모임에_참여한다(accessToken, groupId)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", Matchers.is("GROUP_016"));
    }

    @DisplayName("존재하지 않은 모임에 참여한다")
    @Test
    void participateToNonExistentGroup() {
        모임에_참여한다(hostAccessToken, 0L)
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("message", Matchers.is("GROUP_001"));
    }

    @DisplayName("비회원이 모임에 참여한다")
    @Test
    void participateByNonMember() {
        모임에_참여한다(groupId).statusCode(HttpStatus.UNAUTHORIZED.value())
                .body("message", Matchers.is("AUTH_003"));
    }

    @DisplayName("정원이 가득 찬 모임에 참여한다")
    @Test
    void participateFullGroup() {
        String accessToken = MOMO.로_로그인한다();
        모임에_참여한다(accessToken, groupCapacityTwoId)
                .statusCode(HttpStatus.OK.value());

        accessToken = GUGU.로_로그인한다();
        모임에_참여한다(accessToken, groupCapacityTwoId)
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("모임의 참여자 목록을 조회한다")
    @Test
    void findParticipants() {
        String accessToken = MOMO.로_로그인한다();

        모임에_참여한다(accessToken, groupId)
                .statusCode(HttpStatus.OK.value());

        참여목록을_조회한다(groupId)
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("모임을 탈퇴한다")
    @Test
    void delete() {
        String accessToken = MOMO.로_로그인한다();
        모임에_참여한다(accessToken, groupId).statusCode(HttpStatus.OK.value());

        모임을_탈퇴한다(accessToken, groupId).statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("주최자일 경우 모임에 탈퇴할 수 없다")
    @Test
    void deleteHost() {
        모임을_탈퇴한다(hostAccessToken, groupId).statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("모임에 참여하지 않았으면 탈퇴할 수 없다")
    @Test
    void deleteNotParticipant() {
        String accessToken = MOMO.로_로그인한다();

        모임을_탈퇴한다(accessToken, groupId).statusCode(HttpStatus.FORBIDDEN.value());
    }

    @DisplayName("조기 종료된 모임에는 탈퇴할 수 없다")
    @Test
    void deleteEarlyClosed() {
        String accessToken = MOMO.로_로그인한다();
        모임에_참여한다(accessToken, groupId).statusCode(HttpStatus.OK.value());
        모임을_조기마감한다(hostAccessToken, groupId);

        모임을_탈퇴한다(accessToken, groupId).statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
