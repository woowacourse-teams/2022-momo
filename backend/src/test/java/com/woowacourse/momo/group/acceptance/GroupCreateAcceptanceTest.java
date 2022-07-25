package com.woowacourse.momo.group.acceptance;

import static com.woowacourse.momo.auth.acceptance.MemberFixture.MOMO;
import static com.woowacourse.momo.group.acceptance.GroupRestHandler.모임을_생성한다;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.woowacourse.momo.common.acceptance.AcceptanceTest;
import com.woowacourse.momo.group.service.dto.request.GroupRequest;

class GroupCreateAcceptanceTest extends AcceptanceTest {

    private static final GroupFixture GROUP = GroupFixture.MOMO_STUDY;

    @DisplayName("회원이 모임을 생성한다")
    @Test
    void createGroupByMember() {
        String accessToken = MOMO.로_로그인하다();

        모임을_생성한다(accessToken, GROUP).statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("비회원이 모임을 생성한다")
    @Test
    void createGroupByNonMember() {
        GroupRequest request = GroupRestHandler.groupRequest(GROUP);

        모임을_생성한다(GROUP).statusCode(HttpStatus.BAD_REQUEST.value()); // TODO: UNAUTHORIZED
    }
}
