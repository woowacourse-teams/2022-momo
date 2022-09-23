package com.woowacourse.momo.group.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.auth.config.Authenticated;
import com.woowacourse.momo.auth.config.AuthenticatedOptional;
import com.woowacourse.momo.auth.config.AuthenticationOptionalPrincipal;
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

    @AuthenticatedOptional
    @GetMapping("/{groupId}")
    public ResponseEntity<GroupResponse> findGroup(@AuthenticationOptionalPrincipal Long memberId,
                                                   @PathVariable Long groupId) {
        GroupResponse response = groupService.findGroup(groupId, memberId);
        return ResponseEntity.ok(response);
    }

    @AuthenticatedOptional
    @GetMapping
    public ResponseEntity<GroupPageResponse> findAll(@AuthenticationOptionalPrincipal Long memberId,
                                                     @ModelAttribute GroupSearchRequest groupSearchRequest) {
        GroupPageResponse response = groupService.findGroups(groupSearchRequest, memberId);
        return ResponseEntity.ok(response);
    }

    @Authenticated
    @GetMapping("/me/liked")
    public ResponseEntity<GroupPageResponse> findLikedGroups(@AuthenticationPrincipal Long memberId,
                                                             @ModelAttribute GroupSearchRequest groupSearchRequest) {
        GroupPageResponse response = groupService.findLikedGroups(groupSearchRequest, memberId);
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
}
