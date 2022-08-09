package com.woowacourse.momo.auth.config;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class OauthResolver implements HandlerMethodArgumentResolver {

    private final String redirectPath;

    public OauthResolver(String redirectPath) {
        this.redirectPath = redirectPath;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(OauthRedirectUrl.class);
    }

    @Override
    public String resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String protocol = extractProtocol((ServletWebRequest) webRequest);
        String domainHost = webRequest.getHeader(HttpHeaders.HOST);

        return protocol + "://" + domainHost + redirectPath;
    }

    private String extractProtocol(ServletWebRequest request) {
        return request.getRequest()
                .getScheme();
    }
}
