package com.woowacourse.momo.member.acceptance;

import static org.hamcrest.Matchers.startsWith;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.woowacourse.momo.common.acceptance.AcceptanceTest;
import com.woowacourse.momo.common.acceptance.RestAssuredConvenienceMethod;
import com.woowacourse.momo.member.dto.request.SignUpRequest;

@SuppressWarnings("NonAsciiCharacters")
public class MemberAcceptanceTest extends AcceptanceTest {

    @Test
    void 회원_가입() {
        SignUpRequest request = new SignUpRequest("woowa@woowa.com", "wooteco1!", "모모");

        RestAssuredConvenienceMethod.postRequest(request, "/api/members")
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", startsWith("/api/members"));
    }
}
