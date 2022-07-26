package com.woowacourse.momo.participant.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import static com.woowacourse.momo.common.acceptance.Fixture.로그인;
import static com.woowacourse.momo.common.acceptance.Fixture.회원_가입;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import com.woowacourse.momo.common.acceptance.AcceptanceTest;
import com.woowacourse.momo.common.acceptance.RestHandler;

@SuppressWarnings("NonAsciiCharacters")
public class ParticipantAcceptanceTest extends AcceptanceTest {

    private static String hostToken;
    private static String memberToken;
    private static final String BODY = "{\n" +
            "\t\"name\" : \"모두 모여라 회의\",\n" +
            "\t\"hostId\" : 1,\n" +
            "\t\"categoryId\" : 1,\n" +
            "\t\"duration\" : {\n" +
            "\t\t\"start\" : \"2022-07-01\",\n" +
            "\t\t\"end\" : \"2022-07-01\"\n" +
            "\t},\n" +
            "\t\"schedules\" : [\n" +
            "\t\t{\n" +
            "\t\t\t\"date\" : \"2022-07-01\",\n" +
            "\t\t\t\"startTime\" : \"13:00\",\n" +
            "\t\t\t\"endTime\" : \"15:00\"\n" +
            "\t\t}\n" +
            "\t],\n" +
            "\t\"deadline\" : \"2022-06-30T23:59\",\n" +
            "\t\"location\" : \"루터회관 1층\",\n" +
            "\t\"description\" : \"팀프로젝트 진행\"\n" +
            "}";

    @BeforeEach
    void init() {
        회원_가입("host@woowacoure.com", "1234asdf!", "모모");
        hostToken = 로그인("host@woowacoure.com", "1234asdf!");

        회원_가입("member@woowacoure.com", "1234asdf!", "모모");
        memberToken = 로그인("member@woowacoure.com", "1234asdf!");
    }

    @Test
    void 모임_참여() {
        ExtractableResponse<Response> response = 모임에_참여한다(memberToken, 생성한_모임_아이디());

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void 존재하는_모임에_참여해야_한다() {
        ExtractableResponse<Response> response = 모임에_참여한다(memberToken, 0);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.body().jsonPath().getString("message")).isEqualTo("존재하지 않는 모임입니다.")
        );
    }

    @Test
    void 비회원은_모임에_참여할_수_없다() {
        long groupId = 생성한_모임_아이디();
        탈퇴한다(memberToken);
        ExtractableResponse<Response> response = 모임에_참여한다(memberToken, groupId);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.body().jsonPath().getString("message")).isEqualTo("존재하지 않는 회원입니다.")
        );
    }

    @Test
    void 모임의_참여자_목록_조회() {
        long groupId = 생성한_모임_아이디();
        모임에_참여한다(memberToken, groupId);

        ExtractableResponse<Response> response = 모임의_참여자_목록을_조회한다(groupId);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.body().jsonPath().getString("[0].name")).isEqualTo("모모")
        );
    }

    private ExtractableResponse<Response> 탈퇴한다(String token) {
        return RestHandler.deleteRequestWithToken2(token, "/api/members");
    }

    private long 생성한_모임_아이디() {
        ExtractableResponse<Response> response = RestHandler.postRequestWithTokenAndBody(hostToken, BODY, "/api/groups");
        return response.jsonPath().getLong("groupId");
    }

    private ExtractableResponse<Response> 모임에_참여한다(String token, long groupId) {
        return RestHandler.postRequestWithToken2(token, "/api/groups/" + groupId + "/participants");
    }

    private ExtractableResponse<Response> 모임의_참여자_목록을_조회한다(long groupId) {
        return RestHandler.getRequest2("/api/groups/" + groupId + "/participants");
    }
}
