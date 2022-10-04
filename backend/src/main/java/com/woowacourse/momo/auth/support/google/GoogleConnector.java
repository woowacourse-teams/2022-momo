package com.woowacourse.momo.auth.support.google;

import java.util.Collections;
import java.util.Optional;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.auth.exception.AuthErrorCode;
import com.woowacourse.momo.auth.support.google.dto.GoogleTokenResponse;
import com.woowacourse.momo.auth.support.google.dto.GoogleUserResponse;
import com.woowacourse.momo.global.exception.exception.MomoException;

@Getter
@RequiredArgsConstructor
@Component
public class GoogleConnector {

    private static final String BEARER_TYPE = "Bearer";
    private static final RestTemplate REST_TEMPLATE = new RestTemplate();

    private final GoogleProvider provider;

    public ResponseEntity<GoogleUserResponse> requestUserInfo(String code, String redirectUrl) {
        String googleToken = requestAccessToken(code, redirectUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", String.join(" ", BEARER_TYPE, googleToken));
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        return REST_TEMPLATE.exchange(provider.getUserInfoUrl(), HttpMethod.GET, entity, GoogleUserResponse.class);
    }

    private String requestAccessToken(String code, String redirectUrl) {
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(createBody(code, redirectUrl), createHeaders());

        ResponseEntity<GoogleTokenResponse> response = REST_TEMPLATE.postForEntity(provider.getAccessTokenUrl(),
                entity, GoogleTokenResponse.class);

        return extractAccessToken(response);
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return headers;
    }

    private MultiValueMap<String, String> createBody(String code, String redirectUrl) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", code);
        body.add("client_id", provider.getClientId());
        body.add("client_secret", provider.getClientSecret());
        body.add("redirect_uri", redirectUrl);
        body.add("grant_type", provider.getGrantType());
        return body;
    }

    private String extractAccessToken(ResponseEntity<GoogleTokenResponse> responseEntity) {
        validateResponseStatusOk(responseEntity.getStatusCode());

        GoogleTokenResponse response = Optional.ofNullable(responseEntity.getBody())
                .orElseThrow(() -> new MomoException(AuthErrorCode.OAUTH_ACCESS_TOKEN_REQUEST_FAILED_BY_NON_EXIST_BODY));

        return response.getAccessToken();
    }

    private void validateResponseStatusOk(HttpStatus status) {
        if (!status.is2xxSuccessful()) {
            throw new MomoException(AuthErrorCode.OAUTH_ACCESS_TOKEN_REQUEST_FAILED_BY_NON_2XX_STATUS);
        }
    }
}
