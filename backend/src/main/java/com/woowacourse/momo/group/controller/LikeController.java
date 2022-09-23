package com.woowacourse.momo.group.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.auth.config.Authenticated;
import com.woowacourse.momo.auth.config.AuthenticationPrincipal;
import com.woowacourse.momo.group.service.LikeService;

@RequiredArgsConstructor
@RequestMapping("/api/groups/{groupId}/like")
@RestController
public class LikeController {

    private final LikeService likeService;

    @Authenticated
    @PostMapping
    public ResponseEntity<Void> like(@AuthenticationPrincipal Long memberId, @PathVariable Long groupId) {
        likeService.like(groupId, memberId);
        return ResponseEntity.ok().build();
    }

    @Authenticated
    @DeleteMapping
    public ResponseEntity<Void> leave(@AuthenticationPrincipal Long memberId, @PathVariable Long groupId) {
        likeService.cancel(groupId, memberId);
        return ResponseEntity.noContent().build();
    }
}
