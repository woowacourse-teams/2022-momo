package com.woowacourse.momo.support.logging;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class TraceExtractor {

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
