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

    private static final SimpleDateFormat YEAR_FOLDER_FORMAT = new SimpleDateFormat("yyyy");
    private static final SimpleDateFormat MONTH_FOLDER_FORMAT = new SimpleDateFormat("MM");
    private static final SimpleDateFormat FILE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private static final String EXTENSION = ".txt";
    private static final String LOG_DIRECTORY_BASE_PATH = "./src/log/";

    private static final boolean IS_APPENDED = true;

    public static void writeExceptionStackTrace(String exceptionStackTrace) {
        String filePath = createDirectory();
        File file = new File(filePath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, IS_APPENDED))) {
            writer.append(exceptionStackTrace);
            writer.newLine();
        } catch (IOException e) {
            throw new LogException("로그 작성에 실패하였습니다");
        }
    }

    public static void writeExceptionMessage(Exception exception) {
        String filePath = createDirectory();
        File file = new File(filePath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, IS_APPENDED))) {
            writeExceptionMessage(exception, writer);
            writer.newLine();
        } catch (IOException e) {
            throw new LogException("로그 작성에 실패하였습니다");
        }
    }

    private static String createDirectory() {
        Date today = new Date();

        File baseDirectory = new File(LOG_DIRECTORY_BASE_PATH);
        File yearDirectory = new File(LOG_DIRECTORY_BASE_PATH + YEAR_FOLDER_FORMAT.format(today));
        File monthDirectory = new File(yearDirectory + "/" + MONTH_FOLDER_FORMAT.format(today) + "/");

        createDirectoryOrFile(baseDirectory);
        createDirectoryOrFile(yearDirectory);
        createDirectoryOrFile(monthDirectory);

        return monthDirectory.getPath() + "/" + getLogFileName();
    }

    private static void createDirectoryOrFile(File file) {
        if (!file.exists() && !file.mkdir()) {
            throw new LogException("로그 폴더 생성에 실패하였습니다");
        }
    }

    private static String getLogFileName() {
        Date today = new Date();
        return FILE_FORMAT.format(today) + EXTENSION;
    }

    private static void writeExceptionMessage(Exception exception, BufferedWriter writer) throws IOException {
        writer.append(String.valueOf(exception.getClass()))
                .append(": ")
                .append(exception.getMessage());
        writer.newLine();
    }
}
