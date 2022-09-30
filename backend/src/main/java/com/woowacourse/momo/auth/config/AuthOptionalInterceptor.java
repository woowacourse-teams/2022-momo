package com.woowacourse.momo.auth.config;

import java.lang.annotation.Annotation;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.cors.CorsUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.auth.exception.AuthErrorCode;
import com.woowacourse.momo.auth.exception.AuthException;
import com.woowacourse.momo.auth.support.AuthorizationExtractor;
import com.woowacourse.momo.auth.support.JwtTokenProvider;
import com.woowacourse.momo.global.exception.exception.GlobalErrorCode;
import com.woowacourse.momo.global.exception.exception.MomoException;

@RequiredArgsConstructor
public class AuthOptionalInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (CorsUtils.isPreFlightRequest(request)) {
            return true;
        }

        Optional<AuthenticatedOptional> authenticated = parseAnnotation((HandlerMethod) handler,
                AuthenticatedOptional.class);
        if (authenticated.isPresent()) {
            validateToken(request);
        }
        return true;
    }

    private <T extends Annotation> Optional<T> parseAnnotation(HandlerMethod handler, Class<T> clazz) {
        return Optional.ofNullable(handler.getMethodAnnotation(clazz));
    }

    private void validateToken(HttpServletRequest request) {
        if (AuthorizationExtractor.hasNotAuthHeader(request)) {
            return;
        }

        String token = AuthorizationExtractor.extract(request);
        if (jwtTokenProvider.validateTokenNotUsable(token)) {
            throw new AuthException(AuthErrorCode.AUTH_INVALID_TOKEN);
        }
    }
}
