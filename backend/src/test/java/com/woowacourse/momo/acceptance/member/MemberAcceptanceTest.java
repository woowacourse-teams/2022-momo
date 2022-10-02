package com.woowacourse.momo.acceptance.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.List;
import java.util.stream.Collectors;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.woowacourse.momo.acceptance.AcceptanceTest;
import com.woowacourse.momo.acceptance.auth.AuthRestHandler;
import com.woowacourse.momo.acceptance.group.GroupRestHandler;
import com.woowacourse.momo.acceptance.participant.ParticipantRestHandler;
import com.woowacourse.momo.fixture.GroupFixture;
import com.woowacourse.momo.fixture.MemberFixture;
import com.woowacourse.momo.group.domain.Group;

@SuppressWarnings("NonAsciiCharacters")
class MemberAcceptanceTest extends AcceptanceTest {

    private static final MemberFixture MEMBER = MemberFixture.MOMO;

    private String accessToken;

    @BeforeEach
    void setUp() {
        accessToken = MEMBER.로_로그인한다();
    }

    @DisplayName("개인정보를 조회하다")
    @Test
    void findMyInfo() {
        MemberRestHandler.개인정보를_조회한다(accessToken)
                .statusCode(HttpStatus.OK.value())
                .body("userId", is(MEMBER.getUserId()))
                .body("name", is(MEMBER.getName()));
    }

    @DisplayName("비밀번호를 수정하다")
    @Test
    void updatePassword() {
        String newPassword = "newPassword123!";

        MemberRestHandler.비밀번호를_수정한다(accessToken, newPassword, MEMBER.getPassword())
                .statusCode(HttpStatus.OK.value());

        AuthRestHandler.로그인을_한다(MEMBER.getUserId(), newPassword)
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("이름을 수정하다")
    @Test
    void updateName() {
        String expected = MEMBER.getName() + "new";

        MemberRestHandler.이름을_수정한다(accessToken, expected)
                .statusCode(HttpStatus.OK.value());

        MemberRestHandler.개인정보를_조회한다(accessToken)
                .body("name", is(expected));
    }

    @DisplayName("회원탈퇴를 하다")
    @Test
    void delete() {
        MemberRestHandler.회원탈퇴를_한다(accessToken)
                .statusCode(HttpStatus.NO_CONTENT.value());

        AuthRestHandler.로그인을_한다(MEMBER.getUserId(), MEMBER.getPassword())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", Matchers.is("MEMBER_012"));
    }

    @DisplayName("회원 탈퇴 시 참여한 모임 중 진행중인 모임이 있을 경우 모임에 탈퇴시킨다")
    @Test
    void deleteAndLeave() {
        String hostAccessToken = MemberFixture.DUDU.로_로그인한다();
        Long groupId = GroupFixture.DUDU_COFFEE_TIME.을_생성한다(hostAccessToken);
        ParticipantRestHandler.모임에_참여한다(accessToken, groupId);

        MemberRestHandler.회원탈퇴를_한다(accessToken)
                .statusCode(HttpStatus.NO_CONTENT.value());

        List<Long> groups = GroupRestHandler.본인이_참여한_모임을_조회한다(accessToken)
                .extract()
                .jsonPath()
                .getList("groups", Group.class)
                .stream()
                .map(Group::getId)
                .collect(Collectors.toList());
        assertThat(groups).isEmpty();
    }

    @DisplayName("회원 탈퇴 시 주최한 모임 중 진행중인 모임이 있을 경우 탈퇴할 수 없다")
    @Test
    void deleteExistInProgressGroup() {
        GroupRestHandler.모임을_생성한다(accessToken, GroupFixture.DUDU_COFFEE_TIME);

        MemberRestHandler.회원탈퇴를_한다(accessToken)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", Matchers.is("MEMBER_003"));
    }
}
