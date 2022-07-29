package com.woowacourse.momo.logging;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MomoLogFile {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final String LOG_DIRECTORY_PATH = "./src/log/";
    private static final String EXTENSION = ".txt";

    private MomoLogFile() {
    }

    public static void write(Exception exception) {
        createDirectory();

        File file = new File(LOG_DIRECTORY_PATH + getFileName() + EXTENSION);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writeExceptionMessage(exception, writer);
            writeStackTrace(exception, writer);
            writer.newLine();
        } catch (IOException e) {
            throw new IllegalArgumentException("로그 작성에 문제있음!!!"); // TODO: 수정 필요
        }
    }

    private static void writeExceptionMessage(Exception exception, BufferedWriter writer) throws IOException {
        writer.append(String.valueOf(exception.getClass()))
                .append(": ")
                .append(exception.getMessage());
        writer.newLine();
    }

    private static void writeStackTrace(Exception exception, BufferedWriter writer) throws IOException {
        for (StackTraceElement log : exception.getStackTrace()) {
            writer.append(log.toString());
            writer.newLine();
        }
    }

    private static void createDirectory() {
        File directory = new File(LOG_DIRECTORY_PATH);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    private static String getFileName() {
        return DATE_FORMAT.format(new Date());
    }
}
