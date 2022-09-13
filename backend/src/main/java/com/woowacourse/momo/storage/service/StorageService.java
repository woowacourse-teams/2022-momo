package com.woowacourse.momo.storage.service;

import static org.springframework.http.MediaType.IMAGE_GIF_VALUE;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.woowacourse.momo.global.exception.exception.GlobalErrorCode;
import com.woowacourse.momo.global.exception.exception.MomoException;

@Service
public class StorageService {

    private static final String PATH_PREFIX = "./image-save/";
    private static final List<String> IMAGE_CONTENT_TYPES = List.of(IMAGE_GIF_VALUE, IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE);

    public String save(MultipartFile requestFile) {
        validateContentType(requestFile);

        File savedFile = new File(PATH_PREFIX + requestFile.getOriginalFilename());
        File directory = new File(PATH_PREFIX);

        fileInit(savedFile, directory);

        try (OutputStream outputStream = new FileOutputStream(savedFile)) {
            outputStream.write(requestFile.getBytes());
        } catch (IOException e) {
            throw new MomoException(GlobalErrorCode.FILE_IO_ERROR);
        }

        return savedFile.getName();
    }

    private void validateContentType(MultipartFile file) {
        String contentType = file.getContentType();

        if (contentType == null || isContentTypeNotImage(contentType)) {
            throw new MomoException(GlobalErrorCode.FILE_INVALID_EXTENSION);
        }
    }

    private boolean isContentTypeNotImage(String contentType) {
        return IMAGE_CONTENT_TYPES.stream()
                .noneMatch(contentType::equals);
    }

    private void fileInit(File temporary, File directory) {
        try {
            if (!directory.exists()) {
                directory.mkdirs();
            }
            temporary.createNewFile();
        } catch (IOException e) {
            throw new MomoException(GlobalErrorCode.FILE_IO_ERROR);
        }
    }

    public byte[] load(String fileName) {
        File file = new File(PATH_PREFIX + fileName);
        try (InputStream inputStream = new FileInputStream(file)) {
            return inputStream.readAllBytes();
        } catch (IOException e) {
            throw new MomoException(GlobalErrorCode.FILE_IO_ERROR);
        }
    }
}
