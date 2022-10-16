package com.woowacourse.momo.acceptance.favorite;

import static com.woowacourse.momo.acceptance.favorite.FavoriteRestHandler.모임을_찜을_취소한다;
import static com.woowacourse.momo.acceptance.favorite.FavoriteRestHandler.모임을_찜한다;
import static com.woowacourse.momo.fixture.MemberFixture.MOMO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.woowacourse.momo.acceptance.AcceptanceTest;
import com.woowacourse.momo.fixture.GroupFixture;
import com.woowacourse.momo.fixture.MemberFixture;

@SuppressWarnings("NonAsciiCharacters")
public class FavoriteAcceptanceTest extends AcceptanceTest {

    private static final MemberFixture HOST = MemberFixture.DUDU;
    private static final GroupFixture GROUP = GroupFixture.DUDU_STUDY;

    private String hostAccessToken;
    private Long groupId;

    @BeforeEach
    void setUp() {
        hostAccessToken = HOST.로_로그인한다();
        groupId = GROUP.을_생성한다(hostAccessToken);
    }

    @DisplayName("회원이 모임을 찜한다")
    @Test
    void like() {
        String accessToken = MOMO.로_로그인한다();
        모임을_찜한다(accessToken, groupId)
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("회원이 모임 찜하기를 취소한다.")
    @Test
    void cancel() {
        String accessToken = MOMO.로_로그인한다();
        모임을_찜한다(accessToken, groupId)
                .statusCode(HttpStatus.OK.value());

        모임을_찜을_취소한다(accessToken, groupId)
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
