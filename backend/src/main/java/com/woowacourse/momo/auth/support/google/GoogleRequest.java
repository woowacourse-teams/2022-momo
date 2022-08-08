package com.woowacourse.momo.auth.support.google;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class GoogleRequest {

    private static final String GRANT_TYPE = "authorization_code";

    private String code;
    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String grantType = GRANT_TYPE;
}
