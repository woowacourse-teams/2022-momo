package com.woowacourse.momo.storage.controller;

import java.net.URI;

import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.auth.config.Authenticated;
import com.woowacourse.momo.auth.config.AuthenticationPrincipal;
import com.woowacourse.momo.storage.service.GroupImageService;

@Authenticated
@RequiredArgsConstructor
@RequestMapping("/api/groups/{groupId}/thumbnail")
@RestController
public class GroupImageController {

    private final GroupImageService groupImageService;

    @PostMapping
    public ResponseEntity<Void> update(@AuthenticationPrincipal Long memberId, @PathVariable Long groupId,
                                       @RequestParam("file") @NotNull MultipartFile multipartFile) {
        String fullPathOfSavedImage = groupImageService.update(memberId, groupId, multipartFile);
        return ResponseEntity.created(URI.create(fullPathOfSavedImage)).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> init(@AuthenticationPrincipal Long memberId, @PathVariable Long groupId) {
        groupImageService.init(memberId, groupId);
        return ResponseEntity.noContent().build();
    }
}
