package com.woowacourse.momo.group.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.auth.config.Authenticated;
import com.woowacourse.momo.auth.config.AuthenticationPrincipal;
import com.woowacourse.momo.group.service.GroupService;
import com.woowacourse.momo.group.service.dto.request.GroupFindRequest;
import com.woowacourse.momo.group.service.dto.request.GroupRequest;
import com.woowacourse.momo.group.service.dto.request.GroupUpdateRequest;
import com.woowacourse.momo.group.service.dto.response.GroupIdResponse;
import com.woowacourse.momo.group.service.dto.response.GroupPageResponse;
import com.woowacourse.momo.group.service.dto.response.GroupResponse;

@RequiredArgsConstructor
@RequestMapping("/api/groups")
@RestController
public class GroupController {

    private final GroupService groupService;

    @Authenticated
    @PostMapping
    public ResponseEntity<GroupIdResponse> create(@AuthenticationPrincipal Long memberId,
                                                  @RequestBody GroupRequest groupRequest) {
        GroupIdResponse groupIdResponse = groupService.create(memberId, groupRequest);
        return ResponseEntity.created(URI.create("/api/groups/" + groupIdResponse.getGroupId()))
                .body(groupIdResponse);
    }

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

    @Authenticated
    @PutMapping("/{groupId}")
    public ResponseEntity<Void> update(@AuthenticationPrincipal Long memberId, @PathVariable Long groupId,
                                       @RequestBody GroupUpdateRequest groupUpdateRequest) {
        groupService.update(memberId, groupId, groupUpdateRequest);
        return ResponseEntity.ok().build();
    }

    @Authenticated
    @PostMapping("/{groupId}/close")
    public ResponseEntity<Void> closeEarly(@AuthenticationPrincipal Long memberId, @PathVariable Long groupId) {
        groupService.closeEarly(memberId, groupId);
        return ResponseEntity.ok().build();
    }

    @Authenticated
    @DeleteMapping("/{groupId}")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal Long memberId, @PathVariable Long groupId) {
        groupService.delete(memberId, groupId);
        return ResponseEntity.noContent().build();
    }
}
