package com.woowacourse.momo.acceptance;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

@Component
public class RestHandler {

    private static boolean showLog;

    @Value("${acceptance.show}")
    public void setShowLog(boolean value) {
        showLog = value;
    }

    private static ValidatableResponse request(Function<RequestSpecification, Response> function) {
        if (showLog) {
            RequestSpecification given = RestAssured.given().log().all();
            Response response = function.apply(given);
            return response.then().log().all();
        }
        RequestSpecification given = RestAssured.given();
        Response response = function.apply(given);
        return response.then();
    }

    public static ValidatableResponse getRequest(String path) {
        return request(given -> given
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get(path)
        );
    }

    public static ValidatableResponse getRequest(String accessToken, String path) {
        return request(given -> given
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get(path)
        );
    }

    public static ValidatableResponse postRequest(String path) {
        return request(given -> given
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post(path)
        );
    }

    public static ValidatableResponse postRequest(Object body, String path) {
        return request(given -> given
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post(path)
        );
    }

    public static ValidatableResponse postRequest(String accessToken, String path) {
        return request(given -> given
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post(path)
        );
    }

    public static ValidatableResponse postRequest(String accessToken, Object body, String path) {
        return request(given -> given
                .auth().oauth2(accessToken)
                .body(body)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post(path)
        );
    }

    public static ValidatableResponse postRequestWithNoBody(String accessToken, String path) {
        return request(given -> given
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post(path)
        );
    }

    public static ValidatableResponse putRequest(Object body, String path) {
        return request(given -> given
                .body(body)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .put(path)
        );
    }

    public static ValidatableResponse putRequest(String accessToken, Object body, String path) {
        return request(given -> given
                .auth().oauth2(accessToken)
                .body(body)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .put(path)
        );
    }

    public static ValidatableResponse patchRequest(String accessToken, Object body, String path) {
        return request(given -> given
                .auth().oauth2(accessToken)
                .body(body)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .patch(path)
        );
    }

    public static ValidatableResponse deleteRequest(String path) {
        return request(given -> given
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete(path)
        );
    }

    public static ValidatableResponse deleteRequest(String accessToken, String path) {
        return request(given -> given
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete(path)
        );
    }
}
