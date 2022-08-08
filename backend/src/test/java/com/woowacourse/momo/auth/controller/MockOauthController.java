package com.woowacourse.momo.auth.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.woowacourse.momo.auth.support.google.dto.GoogleTokenResponse;
import com.woowacourse.momo.auth.support.google.dto.GoogleUserResponse;

@RestController
public class MockOauthController {

    @PostMapping(path = "/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<GoogleTokenResponse> getAccessToken(GoogleTokenRequest request) {
        GoogleTokenResponse response = new GoogleTokenResponse("token");
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(path = "/userinfo")
    public ResponseEntity<GoogleUserResponse> getUserinfo() {
        GoogleUserResponse response = new GoogleUserResponse("name", "email@woowahan.com");
        return ResponseEntity.ok().body(response);
    }

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    @AllArgsConstructor
    private static class GoogleTokenRequest {

        private String code;
        private String clientId;
        private String clientSecret;
        private String redirectUri;
        private String grantType;
    }
}
