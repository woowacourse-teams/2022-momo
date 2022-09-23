package com.woowacourse.momo.storage.controller;

import java.net.URI;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.storage.service.StorageService;

@RestController
@RequestMapping("/api/images/")
@RequiredArgsConstructor
public class ImageController {

    private final StorageService storageService;

    @PostMapping
    public ResponseEntity<Void> imageUpload(@RequestParam("imageFile") MultipartFile imageFile) {
        String fileName = storageService.save(imageFile);

        return ResponseEntity.created(URI.create("/api/images/" + fileName)).build();
    }

    @GetMapping(
            value = "{imageFileName}",
            produces = {MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE}
    )
    public ResponseEntity<byte[]> serveImage(@PathVariable String imageFileName) {
        byte[] imageBytes = storageService.load(imageFileName);

        return ResponseEntity.ok(imageBytes);
    }
}
