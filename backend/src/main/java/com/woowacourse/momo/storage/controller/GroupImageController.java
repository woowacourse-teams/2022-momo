package com.woowacourse.momo.storage.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.storage.service.GroupImageService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/groups/{groupId}/thumnail")
public class GroupImageController {

    private final GroupImageService groupImageService;

    @GetMapping(
            produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE}
    )
    public ResponseEntity<byte[]> imageLoad(@PathVariable Long groupId) {
        byte[] imageBytes = groupImageService.load(groupId);

        return ResponseEntity.ok(imageBytes);
    }
}
