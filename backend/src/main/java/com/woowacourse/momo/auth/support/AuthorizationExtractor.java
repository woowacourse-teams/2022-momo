package com.woowacourse.momo.auth.support;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import com.woowacourse.momo.auth.exception.AuthErrorCode;
import com.woowacourse.momo.auth.exception.AuthException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthorizationExtractor {

    private static final String AUTHORIZATION = "Authorization";
    private static final String ACCESS_TOKEN_TYPE = AuthorizationExtractor.class.getSimpleName() + ".ACCESS_TOKEN_TYPE";
    private static final String BEARER_TYPE = "Bearer";

    public static String extract(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaders(AUTHORIZATION);
        while (headers.hasMoreElements()) {
            String value = headers.nextElement();
            if ((value.toLowerCase().startsWith(BEARER_TYPE.toLowerCase()))) {
                String authHeaderValue = value.substring(BEARER_TYPE.length()).trim();
                request.setAttribute(ACCESS_TOKEN_TYPE, value.substring(0, BEARER_TYPE.length()).trim());
                int commaIndex = authHeaderValue.indexOf(',');
                if (commaIndex > 0) {
                    authHeaderValue = authHeaderValue.substring(0, commaIndex);
                }
                return authHeaderValue;
            }
        }

        throw new AuthException(AuthErrorCode.AUTH_REQUIRED_LOGIN);
    }

    public static boolean hasNotAuthHeader(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaders(AUTHORIZATION);
        return !headers.hasMoreElements();
    }
}
