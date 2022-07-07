package com.woowacourse.momo.acceptance;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import static org.hamcrest.Matchers.*;

@Sql("classpath:init.sql")
@SuppressWarnings("NonAsciiCharacters")
public class GroupAcceptanceTest extends AcceptanceTest {

    @Test
    void 모임_생성() {
        String body = "{\n" +
                "\t\"name\" : \"모두 모여라 회의\",\n" +
                "\t\"hostId\" : 1,\n" +
                "\t\"categoryId\" : 1,\n" +
                "\t\"regular\" : false,\n" +
                "\t\"duration\" : {\n" +
                "\t\t\"start\" : \"2022-07-01\",\n" +
                "\t\t\"end\" : \"2022-07-01\"\n" +
                "\t},\n" +
                "\t\"schedules\" : [\n" +
                "\t\t{\n" +
                "\t\t\t\"day\" : \"금\",\n" +
                "\t\t\t\"time\" : {\n" +
                "\t\t\t\t\"start\" : \"13:00\",\n" +
                "\t\t\t\t\"end\" : \"15:00\"\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t],\n" +
                "\t\"deadline\" : \"2022-06-30 23:59\",\n" +
                "\t\"location\" : \"루터회관 1층\",\n" +
                "\t\"description\" : \"팀프로젝트 진행\"\n" +
                "}";

        RestAssuredConvenienceMethod.postRequest(body, "/api/groups")
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", startsWith("/api/groups/"));
    }

    @Test
    void 모임_단일_조회() {
        모임_생성();
        RestAssuredConvenienceMethod.getRequest("/api/groups/1")
                .statusCode(HttpStatus.OK.value())
                .body("name", is("모두 모여라 회의"))
                .body("host.id", is(1))
                .body("host.name", is("momo"))
                .body("categoryId", is(1))
                .body("regular", is(false))
                .body("duration.start", is("2022-07-01"))
                .body("duration.end", is("2022-07-01"))
                .body("schedules[0].day", is("금"))
                .body("schedules[0].time.start", is("13:00:00"))
                .body("schedules[0].time.end", is("15:00:00"))
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

        RestAssuredConvenienceMethod.getRequest("/api/groups")
                .statusCode(HttpStatus.OK.value())
                .body("", hasSize(expected));
    }

    @Test
    void 모임_변경() {
        모임_생성();
        String body = "{\n" +
                "\t\"name\" : \"모두 모여la 회의\",\n" +
                "\t\"categoryId\" : 1,\n" +
                "\t\"regular\" : false,\n" +
                "\t\"duration\" : {\n" +
                "\t\t\"start\" : \"2022-07-01\",\n" +
                "\t\t\"end\" : \"2022-07-01\"\n" +
                "\t},\n" +
                "\t\"schedules\" : [\n" +
                "\t\t{\n" +
                "\t\t\t\"day\" : \"금\",\n" +
                "\t\t\t\"time\" : {\n" +
                "\t\t\t\t\"start\" : \"13:00\",\n" +
                "\t\t\t\t\"end\" : \"15:00\"\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t],\n" +
                "\t\"deadline\" : \"2022-06-30 23:59\",\n" +
                "\t\"location\" : \"루터회관 1층\",\n" +
                "\t\"description\" : \"팀프로젝트 진행\"\n" +
                "}";;

        RestAssuredConvenienceMethod.putRequest(body, "/api/groups/1")
                .statusCode(HttpStatus.OK.value());

        RestAssuredConvenienceMethod.getRequest("/api/groups/1")
                .statusCode(HttpStatus.OK.value())
                .body("name", is("모두 모여la 회의"))
                .body("host.id", is(1))
                .body("host.name", is("momo"))
                .body("categoryId", is(1))
                .body("regular", is(false))
                .body("duration.start", is("2022-07-01"))
                .body("duration.end", is("2022-07-01"))
                .body("schedules[0].day", is("금"))
                .body("schedules[0].time.start", is("13:00:00"))
                .body("schedules[0].time.end", is("15:00:00"))
                .body("deadline", is("2022-06-30 23:59"))
                .body("location", is("루터회관 1층"))
                .body("description", is("팀프로젝트 진행"));
    }
}
