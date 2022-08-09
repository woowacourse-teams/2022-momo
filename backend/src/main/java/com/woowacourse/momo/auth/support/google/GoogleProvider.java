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

    private final String clientId;
    private final String clientSecret;
    private final String authUrl;
    private final String accessTokenUrl;
    private final String userInfoUrl;
    private final String grantType;
    private final String scope;
    private final String temporaryPassword;

    public GoogleProvider(@Value("${oauth2.google.client.id}") String clientId,
                          @Value("${oauth2.google.client.secret}") String clientSecret,
                          @Value("${oauth2.google.url.auth}") String authUrl,
                          @Value("${oauth2.google.url.token}") String accessTokenUrl,
                          @Value("${oauth2.google.url.userinfo}") String userInfoUrl,
                          @Value("${oauth2.google.grant-type}") String grantType,
                          @Value("${oauth2.google.scope}") String scope,
                          @Value("${oauth2.member.temporary-password}") String temporaryPassword) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.authUrl = authUrl;
        this.accessTokenUrl = accessTokenUrl;
        this.userInfoUrl = userInfoUrl;
        this.grantType = grantType;
        this.scope = scope;
        this.temporaryPassword = temporaryPassword;
    }

    public String generateAuthUrl(String redirectUrl) {
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
