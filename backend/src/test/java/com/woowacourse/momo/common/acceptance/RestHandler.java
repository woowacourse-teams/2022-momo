package com.woowacourse.momo.common.acceptance;

import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;

public class RestHandler {

    public static ValidatableResponse getRequest(String path) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get(path)
                .then().log().all();
    }

    public static ValidatableResponse getRequest(String accessToken, String path) {
        return RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get(path)
                .then().log().all();
    }

    public static ValidatableResponse postRequest(Object body, String path) {
        return RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post(path)
                .then().log().all();
    }

    public static ValidatableResponse postRequest(String accessToken, Object body, String path) {
        return RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post(path)
                .then().log().all();
    }

    public static ValidatableResponse putRequest(Object body, String path) {
        return RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .put(path)
                .then().log().all();
    }

    public static ValidatableResponse putRequestWithToken(String accessToken, Object body, String path) {
        return RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .put(path)
                .then().log().all();
    }

    public static ValidatableResponse patchRequest(String accessToken, Object body, String path) {
        return RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .body(body)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .patch(path)
                .then().log().all();
    }

    public static ValidatableResponse deleteRequest(String path) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete(path)
                .then().log().all();
    }

    public static ValidatableResponse deleteRequest(String accessToken, String path) {
        return RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete(path)
                .then().log().all();
    }
}
