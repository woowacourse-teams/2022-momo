package com.woowacourse.momo.global.logging;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.woowacourse.momo.global.logging.exception.LogException;

public class LogFileManager {

    private static final SimpleDateFormat FILE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat LOG_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    private static final String EXTENSION = ".txt";
    private static final boolean IS_APPENDED = true;

    private final String logPath;

    public LogFileManager(String logPath) {
        this.logPath = logPath;
    }

    public void writeExceptionStackTrace(String exceptionStackTrace) {
        Date today = new Date();
        String filePath = createFile(today);
        File file = new File(filePath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, IS_APPENDED))) {
            writer.append(exceptionStackTrace);
            writer.newLine();
        } catch (IOException e) {
            throw new LogException("로그 작성에 실패하였습니다");
        }
    }

    public void writeExceptionMessage(Exception exception) {
        Date today = new Date();
        String filePath = createFile(today);
        File file = new File(filePath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, IS_APPENDED))) {
            writeExceptionMessage(exception, writer, today);
            writer.newLine();
        } catch (IOException e) {
            throw new LogException("로그 작성에 실패하였습니다");
        }
    }

    private String createFile(Date today) {
        File baseDirectory = new File(logPath);
        createDirectory(baseDirectory);

        return baseDirectory.getPath() + "/" + getLogFileName(today);
    }

    private void createDirectory(File file) {
        if (!file.exists() && !file.mkdir()) {
            throw new LogException("로그 폴더 생성에 실패하였습니다");
        }
    }

    private String getLogFileName(Date today) {
        return FILE_FORMAT.format(today) + EXTENSION;
    }

    private void writeExceptionMessage(Exception exception, BufferedWriter writer, Date today) throws IOException {
        writer.append(LOG_DATE_FORMAT.format(today))
                .append(" ")
                .append(String.valueOf(exception.getClass()))
                .append(": ")
                .append(exception.getMessage());
        writer.newLine();
    }
}
