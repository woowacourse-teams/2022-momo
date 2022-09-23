package com.woowacourse.momo.auth.config;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.auth.support.AuthorizationExtractor;
import com.woowacourse.momo.auth.support.JwtTokenProvider;

@RequiredArgsConstructor
public class TokenOptionalResolver implements HandlerMethodArgumentResolver {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationOptionalPrincipal.class);
    }

    @Override
    public Long resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        if (AuthorizationExtractor.hasNotAuthHeader(Objects.requireNonNull(request))) {
            return null;
        }

        String token = AuthorizationExtractor.extract(Objects.requireNonNull(request));

        return jwtTokenProvider.getPayload(token);
    }
}
