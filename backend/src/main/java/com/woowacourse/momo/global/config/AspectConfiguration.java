package com.woowacourse.momo.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.woowacourse.momo.global.logging.ExceptionLogging;
import com.woowacourse.momo.global.logging.LogFileManager;
import com.woowacourse.momo.global.logging.Logging;

@Configuration
@EnableAspectJAutoProxy
public class AspectConfiguration {

    @Value("${momo-logging.file-path}")
    private String logFilePath;

    @Bean
    public LogFileManager logFileManager() {
        return new LogFileManager(logFilePath);
    }

    @Bean
    public Logging logging() {
        return new Logging(logFileManager());
    }

    @ConditionalOnExpression("${momo-logging.show:true}")
    @Bean
    public ExceptionLogging exceptionLogging() {
        return new ExceptionLogging(logging());
    }
}
