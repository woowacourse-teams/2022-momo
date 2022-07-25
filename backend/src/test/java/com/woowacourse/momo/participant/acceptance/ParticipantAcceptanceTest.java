package com.woowacourse.momo.participant.acceptance;

import static com.woowacourse.momo.auth.acceptance.MemberFixture.DUDU;
import static com.woowacourse.momo.participant.acceptance.ParticipantRestHandler.모임에_참여한다;
import static com.woowacourse.momo.participant.acceptance.ParticipantRestHandler.참여목록을_조회한다;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.woowacourse.momo.auth.acceptance.MemberFixture;
import com.woowacourse.momo.common.acceptance.AcceptanceTest;
import com.woowacourse.momo.group.acceptance.GroupFixture;

class ParticipantAcceptanceTest extends AcceptanceTest {

    private static final MemberFixture HOST = MemberFixture.MOMO;
    private static final GroupFixture GROUP = GroupFixture.MOMO_STUDY;

    private String hostAccessToken;
    private Long groupId;

    @BeforeEach
    void setUp() {
        hostAccessToken = HOST.로_로그인하다();
        groupId = GROUP.을_생성한다(hostAccessToken);
    }

    @DisplayName("회원이 모임에 참여한다")
    @Test
    void participateByMember() {
        String accessToken = DUDU.로_로그인하다();
        모임에_참여한다(accessToken, groupId).statusCode(HttpStatus.OK.value());

        참여목록을_조회한다(groupId);
    }

    @DisplayName("주최자가 자신이 주최한 모임에 참여한다")
    @Test
    void participateByHost() {
        모임에_참여한다(hostAccessToken, groupId).statusCode(HttpStatus.OK.value()); // TODO: FORBIDDEN
    }

    @DisplayName("참여자가 자신이 참여한 모임에 재참여한다")
    @Test
    void participateByParticipants() {
        String accessToken = DUDU.로_로그인하다();
        모임에_참여한다(accessToken, groupId).statusCode(HttpStatus.OK.value());
        모임에_참여한다(accessToken, groupId).statusCode(HttpStatus.OK.value());
    }

    @DisplayName("존재하지 않은 모임에 참여한다")
    @Test
    void participateToNonExistentGroup() {
        모임에_참여한다(hostAccessToken, 0L).statusCode(HttpStatus.BAD_REQUEST.value()); // TODO: NOT_FOUND
    }

    @DisplayName("비회원이 모임에 참여한다")
    @Test
    void participateByNonMember() {
        모임에_참여한다(groupId).statusCode(HttpStatus.BAD_REQUEST.value()); // TODO: UNAUTHORIZED
    }
}
