package com.woowacourse.momo.auth.support.google;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class GoogleProvider {

    @Value("${oauth2.url.redirect}")
    private String redirectUrl;

    @Value("${oauth2.google.client.id}")
    private String clientId;

    @Value("${oauth2.google.client.secret}")
    private String clientSecret;

    @Value("${oauth2.google.url.auth}")
    private String authUrl;

    @Value("${oauth2.google.url.token}")
    private String accessTokenUrl;

    @Value("${oauth2.google.url.userinfo}")
    private String userInfoUrl;

    @Value("${oauth2.google.grant-type}")
    private String grantType;

    @Value("${oauth2.google.scope}")
    private String scope;

    @Value("${oauth2.member.temporary-password}")
    private String temporaryPassword;

    public String generateAuthUrl() {
        Map<String, String> params = new HashMap<>();
        params.put("scope", scope);
        params.put("response_type", "code");
        params.put("client_id", clientId);
        params.put("redirect_uri", redirectUrl);
        return authUrl + "?" + concatParams(params);
    }

    private String concatParams(Map<String, String> params) {
        return params.entrySet()
                .stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));
    }
}
