package com.woowacourse.momo.acceptance;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = "classpath:init.sql", executionPhase = BEFORE_TEST_METHOD)
@Sql(value = "classpath:truncate.sql", executionPhase = AFTER_TEST_METHOD)
public class AcceptanceTest {

    @Value("${local.server.port}")
    private int port;

    @BeforeEach
    public void init() {
        RestAssured.port = port;
    }
}
