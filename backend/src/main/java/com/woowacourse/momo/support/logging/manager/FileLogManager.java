package com.woowacourse.momo.support.logging.manager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

import com.woowacourse.momo.support.logging.TraceExtractor;
import com.woowacourse.momo.support.logging.exception.LogException;

public class FileLogManager implements LogManager {

    private static final SimpleDateFormat FILE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat LOG_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    private static final String EXTENSION = ".txt";
    private static final boolean IS_APPENDED = true;

    @Value("${momo-log.file}")
    private boolean used;

    private final String logPath;

    public FileLogManager(String logPath) {
        this.logPath = logPath;
    }

    public void writeMessage(String message) {
        Date today = new Date();
        String filePath = createFile(today);
        File file = new File(filePath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, IS_APPENDED))) {
            writer.append(message);
            writer.newLine();
        } catch (IOException e) {
            throw new LogException("로그 작성에 실패하였습니다");
        }
    }

    public void writeException(Exception exception) {
        Date today = new Date();
        String filePath = createFile(today);
        File file = new File(filePath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, IS_APPENDED))) {
            writeException(exception, writer, today);
            writer.newLine();
        } catch (IOException e) {
            throw new LogException("로그 작성에 실패하였습니다");
        }
    }

    @Override
    public boolean isNotUsed() {
        return !used;
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

    private void writeException(Exception exception, BufferedWriter writer, Date today) throws IOException {
        String stackTrace = TraceExtractor.getStackTrace(exception);
        writer.append(LOG_DATE_FORMAT.format(today))
                .append(" ")
                .append(String.valueOf(exception.getClass()))
                .append(": ")
                .append(stackTrace);
        writer.newLine();
    }
}
