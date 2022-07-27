package com.woowacourse.momo.acceptance.group;

import static com.woowacourse.momo.acceptance.group.GroupRestHandler.모임을_삭제한다;
import static com.woowacourse.momo.acceptance.group.GroupRestHandler.모임을_조회한다;
import static com.woowacourse.momo.fixture.MemberFixture.DUDU;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.woowacourse.momo.acceptance.AcceptanceTest;
import com.woowacourse.momo.fixture.GroupFixture;
import com.woowacourse.momo.fixture.MemberFixture;

class GroupDeleteAcceptanceTest extends AcceptanceTest {

    private static final GroupFixture GROUP = GroupFixture.MOMO_STUDY;
    private static final MemberFixture HOST = MemberFixture.MOMO;

    private String hostAccessToken;
    private Long groupId;

    @BeforeEach
    void setUp() {
        hostAccessToken = HOST.로_로그인한다();
        groupId = GROUP.을_생성한다(hostAccessToken);
    }

    @DisplayName("주최자가 모임을 삭제한다")
    @Test
    void deleteGroupByHost() {
        모임을_삭제한다(hostAccessToken, groupId).statusCode(HttpStatus.NO_CONTENT.value());
        모임을_조회한다(hostAccessToken, groupId).statusCode(HttpStatus.BAD_REQUEST.value());// TODO: NOT_FOUND
    }

    @DisplayName("주최자가 아닌 회원이 모임을 삭제한다")
    @Test
    void deleteGroupByAnotherMember() {
        String anotherAccessToken = DUDU.로_로그인한다();
        모임을_삭제한다(anotherAccessToken, groupId).statusCode(HttpStatus.BAD_REQUEST.value()); // TODO: UNAUTHORIZED
        모임을_조회한다(hostAccessToken, groupId).statusCode(HttpStatus.OK.value());
    }

    @DisplayName("비회원이 모임을 삭제한다")
    @Test
    void deleteGroupByNonMember() {
        모임을_삭제한다(groupId).statusCode(HttpStatus.BAD_REQUEST.value()); // TODO: UNAUTHORIZED
        모임을_조회한다(hostAccessToken, groupId).statusCode(HttpStatus.OK.value());
    }

    @DisplayName("존재하지 않은 모임을 삭제한다")
    @Test
    void deleteNonExistentGroup() {
        모임을_조회한다(hostAccessToken, 0L).statusCode(HttpStatus.BAD_REQUEST.value()); // TODO: NOT_FOUND
        모임을_삭제한다(hostAccessToken, 0L).statusCode(HttpStatus.BAD_REQUEST.value()); // TODO: NOT_FOUND
    }
}
