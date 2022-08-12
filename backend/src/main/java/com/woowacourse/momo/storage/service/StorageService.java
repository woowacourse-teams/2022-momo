package com.woowacourse.momo.storage.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.woowacourse.momo.global.exception.exception.ErrorCode;
import com.woowacourse.momo.global.exception.exception.MomoException;

@Service
public class StorageService {

    private static final String PATH_PREFIX = "./image-save/";

    public String save(MultipartFile requestFile) {
        File savedFile = new File(PATH_PREFIX + requestFile.getOriginalFilename());
        File directory = new File(PATH_PREFIX);

        validateExtension(requestFile);

        fileInit(savedFile, directory);

        try (OutputStream outputStream = new FileOutputStream(savedFile)) {
            outputStream.write(requestFile.getBytes());
        } catch (IOException e) {
            throw new MomoException(ErrorCode.FILE_IO_ERROR);
        }

        return savedFile.getName();
    }

    private void validateExtension(MultipartFile file) {
        String contentType = file.getContentType();

        if (contentType == null || !(contentType.equals(MediaType.IMAGE_GIF_VALUE) ||
                contentType.equals(MediaType.IMAGE_JPEG_VALUE) ||
                contentType.equals(MediaType.IMAGE_PNG_VALUE))) {
            throw new MomoException(ErrorCode.FILE_INVALID_EXTENSION);
        }
    }

    private void fileInit(File temporary, File directory) {
        try {
            if (!directory.exists()) {
                directory.mkdirs();
            }
            temporary.createNewFile();
        } catch (IOException e) {
            throw new MomoException(ErrorCode.FILE_IO_ERROR);
        }
    }

    public byte[] load(String fileName) {
        File file = new File(PATH_PREFIX + fileName);
        try (InputStream inputStream = new FileInputStream(file)) {
            return inputStream.readAllBytes();
        } catch (IOException e) {
            throw new MomoException(ErrorCode.FILE_IO_ERROR);
        }
    }
}
