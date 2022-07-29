package com.woowacourse.momo.logging;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MomoLogFile {

    private MomoLogFile() {
    }

    public static void write(Exception exception) {
        createDirectory();

        File file = new File("./src/log/2022-07-29.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.append(String.valueOf(exception.getClass()))
                    .append(": ")
                    .append(exception.getMessage());
            writer.newLine();
            for (StackTraceElement log : exception.getStackTrace()) {
                writer.append(log.toString());
                writer.newLine();
            }
            writer.newLine();
        } catch (IOException e) {
            throw new IllegalArgumentException("로그 작성에 문제있음!!!"); // TODO: 수정 필요
        }
    }

    private static void createDirectory() {
        File directory = new File("./src/log");
        if (!directory.exists()) {
            directory.mkdir();
        }
    }
}
