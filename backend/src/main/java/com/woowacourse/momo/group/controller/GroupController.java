package com.woowacourse.momo.group.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.auth.config.Authenticated;
import com.woowacourse.momo.auth.config.AuthenticationPrincipal;
import com.woowacourse.momo.group.service.GroupService;
import com.woowacourse.momo.group.service.dto.request.GroupRequest;
import com.woowacourse.momo.group.service.dto.request.GroupUpdateRequest;
import com.woowacourse.momo.group.service.dto.response.GroupIdResponse;
import com.woowacourse.momo.group.service.dto.response.GroupPageResponse;
import com.woowacourse.momo.group.service.dto.response.GroupResponse;
import com.woowacourse.momo.group.service.dto.response.GroupSummaryResponse;

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
    public ResponseEntity<GroupResponse> findById(@PathVariable Long groupId) {
        return ResponseEntity.ok(groupService.findById(groupId));
    }

    @Authenticated
    @GetMapping("/me")
    public ResponseEntity<List<GroupSummaryResponse>> findMyGroup(@AuthenticationPrincipal Long memberId) {
        return ResponseEntity.ok(groupService.findGroupOfMember(memberId));
    }

    @GetMapping(params = {"page"})
    public ResponseEntity<GroupPageResponse> findAll(@RequestParam(required = false, defaultValue = "0") int page) {
        return ResponseEntity.ok(groupService.findAll(page));
    }

    @GetMapping(params = {"category", "page"})
    public ResponseEntity<GroupPageResponse> findAllByCategory(
            @RequestParam long category, @RequestParam(required = false, defaultValue = "0") int page) {
        return ResponseEntity.ok(groupService.findAllByCategory(category, page));
    }

    @GetMapping(params = {"keyword", "page"})
    public ResponseEntity<GroupPageResponse> findAllByKeyword(
            @RequestParam String keyword, @RequestParam(required = false, defaultValue = "0") int page) {
        return ResponseEntity.ok(groupService.findAllByKeyword(keyword, page));
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
