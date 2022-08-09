package com.woowacourse.momo.storage.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageService {

    private static final String PATH_PREFIX = "./image-save/";

    public String save(MultipartFile file) {
        File temporary = new File(PATH_PREFIX + file.getOriginalFilename());
        File directory = new File(PATH_PREFIX);

        fileInit(temporary, directory);

        try (OutputStream outputStream = new FileOutputStream(temporary)) {
            outputStream.write(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return temporary.getName();
    }

    private void fileInit(File temporary, File directory) {
        try {
            if (!directory.exists()) {
                directory.mkdirs();
            }
            temporary.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] load(String fileName) {
        File file = new File(PATH_PREFIX + fileName);
        try (InputStream inputStream = new FileInputStream(file)) {
            return inputStream.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
