package com.woowacourse.momo.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.auth.config.OauthRedirectUrl;
import com.woowacourse.momo.auth.config.OauthRequestUrl;
import com.woowacourse.momo.auth.service.OauthService;
import com.woowacourse.momo.auth.service.dto.response.LoginResponse;
import com.woowacourse.momo.auth.service.dto.response.OauthLinkResponse;

@RequiredArgsConstructor
@RequestMapping("/api/auth/oauth2/google")
@RestController
public class OauthController {

    private final OauthService oauthService;

    @GetMapping("/login")
    public ResponseEntity<OauthLinkResponse> access(@OauthRedirectUrl String redirectUrl) {
        OauthLinkResponse response = oauthService.generateAuthUrl(redirectUrl);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/login", params = {"code"})
    public ResponseEntity<LoginResponse> login(@RequestParam String code, @OauthRequestUrl String requestUrl) {
        LoginResponse loginResponse = oauthService.requestAccessToken(code, requestUrl);
        return ResponseEntity.ok(loginResponse);
    }
}
