package com.woowacourse.momo.group.acceptance;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

import static com.woowacourse.momo.common.acceptance.Fixture.로그인;
import static com.woowacourse.momo.common.acceptance.Fixture.회원_가입;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import com.woowacourse.momo.common.acceptance.AcceptanceTest;
import com.woowacourse.momo.common.acceptance.RestHandler;

@SuppressWarnings("NonAsciiCharacters")
@Sql("classpath:init.sql")
@Sql(value = "classpath:clear.sql", executionPhase = AFTER_TEST_METHOD)
class GroupAcceptanceTest extends AcceptanceTest {

    private static String token;

    @BeforeEach
    void init() {
        회원_가입("email@woowacoure.com", "1234asdf!", "모모");
        token = 로그인("email@woowacoure.com", "1234asdf!");
    }

    @Test
    void 모임_생성() {
        String body = "{\n" +
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
                "\t\"deadline\" : \"2022-06-30 23:59\",\n" +
                "\t\"location\" : \"루터회관 1층\",\n" +
                "\t\"description\" : \"팀프로젝트 진행\"\n" +
                "}";

        RestHandler.postRequestWithToken(token, body, "/api/groups")
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", startsWith("/api/groups/"));
    }

    @Test
    void 모임_단일_조회() {
        모임_생성();
        RestHandler.getRequest("/api/groups/1")
                .statusCode(HttpStatus.OK.value())
                .body("name", is("모두 모여라 회의"))
                .body("host.id", is(1))
                .body("host.name", is("모모"))
                .body("categoryId", is(1))
                .body("duration.start", is("2022-07-01"))
                .body("duration.end", is("2022-07-01"))
                .body("schedules[0].date", is("2022-07-01"))
                .body("schedules[0].startTime", is("13:00:00"))
                .body("schedules[0].endTime", is("15:00:00"))
                .body("deadline", is("2022-06-30 23:59"))
                .body("location", is("루터회관 1층"))
                .body("description", is("팀프로젝트 진행"));
    }

    @Test
    void 모임_전체_조회() {
        int expected = 5;
        for (int i = 0; i < expected; i++) {
            모임_생성();
        }

        RestHandler.getRequest("/api/groups")
                .statusCode(HttpStatus.OK.value())
                .body("", hasSize(expected));
    }

    @Test
    void 모임_변경() {
        모임_생성();
        String body = "{\n" +
                "\t\"name\" : \"모두 모여라 회의222\",\n" +
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
                "\t\"deadline\" : \"2022-06-30 23:59\",\n" +
                "\t\"location\" : \"루터회관 1층\",\n" +
                "\t\"description\" : \"팀프로젝트 진행\"\n" +
                "}";

        RestHandler.putRequestWithToken(token, body, "/api/groups/1")
                .statusCode(HttpStatus.OK.value());

        RestHandler.getRequest("/api/groups/1")
                .statusCode(HttpStatus.OK.value())
                .body("name", is("모두 모여라 회의222"))
                .body("host.id", is(1))
                .body("host.name", is("모모"))
                .body("categoryId", is(1))
                .body("duration.start", is("2022-07-01"))
                .body("duration.end", is("2022-07-01"))
                .body("schedules[0].date", is("2022-07-01"))
                .body("schedules[0].startTime", is("13:00:00"))
                .body("schedules[0].endTime", is("15:00:00"))
                .body("deadline", is("2022-06-30 23:59"))
                .body("location", is("루터회관 1층"))
                .body("description", is("팀프로젝트 진행"));
    }

    @Test
    void 모임_삭제() {
        모임_생성();

        RestHandler.deleteRequestWithToken(token, "/api/groups/1")
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
