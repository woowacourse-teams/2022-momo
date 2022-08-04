package com.woowacourse.momo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.woowacourse.momo.logging.ExceptionLogging;
import com.woowacourse.momo.logging.LogFileManager;
import com.woowacourse.momo.logging.Logging;

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
