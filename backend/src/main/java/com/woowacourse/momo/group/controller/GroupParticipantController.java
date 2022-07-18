package com.woowacourse.momo.group.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.auth.config.AuthenticationPrincipal;
import com.woowacourse.momo.group.service.GroupParticipantService;
import com.woowacourse.momo.member.dto.response.MemberResponse;

@RequiredArgsConstructor
@RequestMapping("/api/groups/{groupId}/participants")
@RestController
public class GroupParticipantController {

    private final GroupParticipantService groupParticipantService;

    @PostMapping
    public ResponseEntity<Void> participate(@AuthenticationPrincipal Long memberId, @PathVariable Long groupId) {
        groupParticipantService.participate(groupId, memberId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<MemberResponse>> findParticipants(@PathVariable Long groupId) {
        return ResponseEntity.ok(groupParticipantService.findParticipants(groupId));
    }
}
