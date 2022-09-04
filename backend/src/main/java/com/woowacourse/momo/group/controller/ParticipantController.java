package com.woowacourse.momo.group.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.auth.config.Authenticated;
import com.woowacourse.momo.auth.config.AuthenticationPrincipal;
import com.woowacourse.momo.group.service.ParticipantService;
import com.woowacourse.momo.member.service.dto.response.MemberResponse;

@RequiredArgsConstructor
@RequestMapping("/api/groups/{groupId}/participants")
@RestController
public class ParticipantController {

    private final ParticipantService participantService;

    @Authenticated
    @PostMapping
    public ResponseEntity<Void> participate(@AuthenticationPrincipal Long memberId, @PathVariable Long groupId) {
        participantService.participate(groupId, memberId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<MemberResponse>> findParticipants(@PathVariable Long groupId) {
        return ResponseEntity.ok(participantService.findParticipants(groupId));
    }

    @Authenticated
    @DeleteMapping
    public ResponseEntity<Void> delete(@AuthenticationPrincipal Long memberId, @PathVariable Long groupId) {
        participantService.delete(groupId, memberId);
        return ResponseEntity.noContent().build();
    }
}