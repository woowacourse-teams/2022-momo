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
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {

    private final StorageService storageService;

    @PostMapping("/members/{memberId}")
    public ResponseEntity<Void> memberImageUpload(@PathVariable Long memberId, @RequestParam("imageFile") MultipartFile imageFile) {
        String fileName = storageService.saveMemberImage(memberId, imageFile);

        return ResponseEntity.created(URI.create("/api/images/members/" + fileName)).build();
    }

    @PostMapping(path = "/groups/{groupId}")
    public ResponseEntity<Void> groupImageUpload(@PathVariable Long groupId, @RequestParam("imageFile") MultipartFile imageFile) {
        String fileName = storageService.saveGroupImage(groupId, imageFile);

        return ResponseEntity.created(URI.create("/api/images/groups/" + fileName)).build();
    }

    @GetMapping(
            value = "/members/{memberId}",
            produces = { MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE }
    )
    public ResponseEntity<byte[]> serveUserImage(@PathVariable Long memberId) {
        byte[] imageBytes = storageService.loadMemberImage(memberId);

        return ResponseEntity.ok(imageBytes);
    }

    @GetMapping(
            value = "/groups/{groupId}",
            produces = { MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE }
    )
    public ResponseEntity<byte[]> serveGroupImage(@PathVariable Long groupId) {
        byte[] imageBytes = storageService.loadGroupImage(groupId);

        return ResponseEntity.ok(imageBytes);
    }
}
