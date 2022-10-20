package com.woowacourse.momo.support.logging;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import org.springframework.transaction.TransactionManager;

public class TraceExtractor {
    
    private TraceExtractor() {}

    public static String getStackTrace(Exception e) {
        try (Writer temporalWriter = new StringWriter();
             PrintWriter printWriter = new PrintWriter(temporalWriter)
        ) {
            e.printStackTrace(printWriter);
            return temporalWriter.toString();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
