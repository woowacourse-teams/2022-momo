package com.woowacourse.momo.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.auth.service.OauthService;
import com.woowacourse.momo.auth.service.dto.response.LoginResponse;
import com.woowacourse.momo.auth.service.dto.response.OauthLinkResponse;

@RequiredArgsConstructor
@RequestMapping("/api/auth/oauth2/google")
@RestController
public class OauthController {

    private final OauthService oauthService;

    @GetMapping(value = "/login", params = {"redirectUrl"})
    public ResponseEntity<OauthLinkResponse> access(@RequestParam String redirectUrl) {
        OauthLinkResponse response = oauthService.generateAuthUrl(redirectUrl);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/login", params = {"code", "redirectUrl"})
    public ResponseEntity<LoginResponse> login(@RequestParam String code, @RequestParam String redirectUrl) {
        LoginResponse loginResponse = oauthService.requestAccessToken(code, redirectUrl);
        return ResponseEntity.ok(loginResponse);
    }
}
