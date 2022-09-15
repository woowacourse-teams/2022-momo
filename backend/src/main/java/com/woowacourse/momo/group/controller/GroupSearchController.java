package com.woowacourse.momo.group.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.auth.config.Authenticated;
import com.woowacourse.momo.auth.config.AuthenticationPrincipal;
import com.woowacourse.momo.group.service.GroupSearchService;
import com.woowacourse.momo.group.service.dto.request.GroupSearchRequest;
import com.woowacourse.momo.group.service.dto.response.GroupPageResponse;
import com.woowacourse.momo.group.service.dto.response.GroupResponse;

@RequiredArgsConstructor
@RequestMapping("/api/groups")
@RestController
public class GroupSearchController {

    private final GroupSearchService groupService;

    @GetMapping("/{groupId}")
    public ResponseEntity<GroupResponse> findGroup(@PathVariable Long groupId) {
        GroupResponse response = groupService.findGroup(groupId);
        return ResponseEntity.ok(response);
    }

    @Authenticated
    @GetMapping("/me/participated")
    public ResponseEntity<GroupPageResponse> findParticipatedGroups(@AuthenticationPrincipal Long memberId,
                                                                    @ModelAttribute GroupSearchRequest groupSearchRequest) {
        GroupPageResponse response = groupService.findParticipatedGroups(groupSearchRequest, memberId);
        return ResponseEntity.ok(response);
    }

    @Authenticated
    @GetMapping("/me/hosted")
    public ResponseEntity<GroupPageResponse> findHostedGroups(@AuthenticationPrincipal Long memberId,
                                                              @ModelAttribute GroupSearchRequest groupSearchRequest) {
        GroupPageResponse response = groupService.findHostedGroups(groupSearchRequest, memberId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<GroupPageResponse> findAll(@ModelAttribute GroupSearchRequest groupSearchRequest) {
        GroupPageResponse response = groupService.findGroups(groupSearchRequest);
        return ResponseEntity.ok(response);
    }
}
