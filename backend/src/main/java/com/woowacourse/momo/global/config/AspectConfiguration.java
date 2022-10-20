package com.woowacourse.momo.global.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.woowacourse.momo.support.logging.ExceptionLogging;
import com.woowacourse.momo.support.logging.Logging;
import com.woowacourse.momo.support.logging.manager.FileLogManager;
import com.woowacourse.momo.support.logging.manager.SlackLogManager;

@Configuration
@EnableAspectJAutoProxy
public class AspectConfiguration {

    @Value("${momo-logging.file-path}")
    private String logFilePath;

    @Bean
    public FileLogManager fileLogManager() {
        return new FileLogManager(logFilePath);
    }

    @Bean
    public SlackLogManager slackLogManager() {
        return new SlackLogManager();
    }

    @Bean
    public Logging logging() {
        return new Logging(fileLogManager(), slackLogManager());
    }

    @ConditionalOnExpression("${momo-logging.show:true}")
    @Bean
    public ExceptionLogging exceptionLogging() {
        return new ExceptionLogging(logging());
    }
}
