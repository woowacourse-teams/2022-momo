package com.woowacourse.momo.logging;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import com.woowacourse.momo.logging.exception.LogException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LogFileManager {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final String LOG_DIRECTORY_PATH = "./src/log/";
    private static final String EXTENSION = ".txt";
    private static final File DIRECTORY = new File(LOG_DIRECTORY_PATH);
    private static final boolean IS_APPENDED = true;

    public static void writeExceptionStackTrace(String exceptionStackTrace) {
        createDirectory();

        File file = new File(LOG_DIRECTORY_PATH + getFileName() + EXTENSION);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, IS_APPENDED))) {
            writer.append(exceptionStackTrace);
            writer.newLine();
        } catch (IOException e) {
            throw new LogException("로그 작성에 실패하였습니다");
        }
    }

    public static void write(Exception exception) {
        createDirectory();

        File file = new File(LOG_DIRECTORY_PATH + getFileName() + EXTENSION);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, IS_APPENDED))) {
            writeExceptionMessage(exception, writer);
            writer.newLine();
        } catch (IOException e) {
            throw new LogException("로그 작성에 실패하였습니다");
        }
    }

    private static void createDirectory() {
        if (!DIRECTORY.exists() && !DIRECTORY.mkdir()) {
            throw new LogException("로그 폴더 생성에 실패하였습니다");
        }
    }

    private static String getFileName() {
        return DATE_FORMAT.format(new Date());
    }

    private static void writeExceptionMessage(Exception exception, BufferedWriter writer) throws IOException {
        writer.append(String.valueOf(exception.getClass()))
                .append(": ")
                .append(exception.getMessage());
        writer.newLine();
    }
}
