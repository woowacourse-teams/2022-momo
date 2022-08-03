package com.woowacourse.momo.auth.config;

import java.lang.annotation.Annotation;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.cors.CorsUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.auth.exception.AuthFailException;
import com.woowacourse.momo.auth.support.AuthorizationExtractor;
import com.woowacourse.momo.auth.support.JwtTokenProvider;

@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (CorsUtils.isPreFlightRequest(request)) {
            return true;
        }

        Optional<Authenticated> authenticated = parseAnnotation((HandlerMethod) handler, Authenticated.class);
        if (authenticated.isPresent()) {
            validateContainsAuthorizationHeader(request);
            validateToken(request);
        }
        return true;
    }

    private void validateContainsAuthorizationHeader(HttpServletRequest request) {
        if (request.getHeader("Authorization") == null) {
            throw new AuthFailException("AUTH_ERROR_003"); // 인증이 필요한 접근입니다, 비회원이 접근 시 로그인이 필요한 경우
        }
    }

    private <T extends Annotation> Optional<T> parseAnnotation(HandlerMethod handler, Class<T> clazz) {
        return Optional.ofNullable(handler.getMethodAnnotation(clazz));
    }

    private void validateToken(HttpServletRequest request) {
        String token = AuthorizationExtractor.extract(request);
        if (!jwtTokenProvider.validateToken(token)) {
            throw new AuthFailException("AUTH_ERROR_002"); // 유효하지 않는 토큰입니다.
        }
    }
}
