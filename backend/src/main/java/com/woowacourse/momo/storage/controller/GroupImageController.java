package com.woowacourse.momo.storage.controller;

import java.net.URI;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.auth.config.AuthenticationPrincipal;
import com.woowacourse.momo.storage.service.GroupImageService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/groups/{groupId}/thumnail")
public class GroupImageController {

    private final GroupImageService groupImageService;

    @PostMapping
    public ResponseEntity<Void> imageSave(
            @PathVariable Long groupId, @AuthenticationPrincipal Long memberId, MultipartFile multipartFile
    ) {
        groupImageService.save(memberId, groupId, multipartFile);
        return ResponseEntity.created(URI.create("/api/groups/" + groupId + "/thumnail")).build();
    }

    @GetMapping(
            produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE}
    )
    public ResponseEntity<byte[]> imageLoad(@PathVariable Long groupId) {
        byte[] imageBytes = groupImageService.load(groupId);

        return ResponseEntity.ok(imageBytes);
    }
}
