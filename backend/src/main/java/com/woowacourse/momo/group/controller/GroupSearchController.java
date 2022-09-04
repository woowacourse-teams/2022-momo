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
import com.woowacourse.momo.group.service.GroupService;
import com.woowacourse.momo.group.service.request.GroupFindRequest;
import com.woowacourse.momo.group.service.response.GroupPageResponse;
import com.woowacourse.momo.group.service.response.GroupResponse;

@RequiredArgsConstructor
@RequestMapping("/api/groups")
@RestController
public class GroupSearchController {

    private final GroupService groupService;

    @GetMapping("/{groupId}")
    public ResponseEntity<GroupResponse> findGroup(@PathVariable Long groupId) {
        return ResponseEntity.ok(groupService.findGroup(groupId));
    }

    @Authenticated
    @GetMapping("/me/participated")
    public ResponseEntity<GroupPageResponse> findParticipatedGroups(@AuthenticationPrincipal Long memberId,
                                                                    @ModelAttribute GroupFindRequest groupFindRequest) {
        return ResponseEntity.ok(groupService.findParticipatedGroups(groupFindRequest, memberId));
    }

    @Authenticated
    @GetMapping("/me/hosted")
    public ResponseEntity<GroupPageResponse> findHostedGroups(@AuthenticationPrincipal Long memberId,
                                                              @ModelAttribute GroupFindRequest groupFindRequest) {
        return ResponseEntity.ok(groupService.findHostedGroups(groupFindRequest, memberId));
    }

    @GetMapping
    public ResponseEntity<GroupPageResponse> findAll(@ModelAttribute GroupFindRequest groupFindRequest) {
        return ResponseEntity.ok(groupService.findGroups(groupFindRequest));
    }
}
