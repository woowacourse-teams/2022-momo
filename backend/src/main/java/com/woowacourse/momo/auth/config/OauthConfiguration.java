package com.woowacourse.momo.auth.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class OauthConfiguration implements WebMvcConfigurer {

    @Value("${oauth2.redirect-path}")
    private String redirectPath;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new OauthRedirectUrlResolver(redirectPath));
        resolvers.add(new OauthRequestUrlResolver());
    }
}
