package com.woowacourse.momo.group.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.group.domain.Group;
import com.woowacourse.momo.group.domain.search.GroupSearchRepository;
import com.woowacourse.momo.group.service.dto.request.GroupSearchRequest;
import com.woowacourse.momo.group.service.dto.response.GroupPageResponse;
import com.woowacourse.momo.group.service.dto.response.GroupResponse;
import com.woowacourse.momo.group.service.dto.response.GroupResponseAssembler;
import com.woowacourse.momo.group.service.dto.response.GroupSummaryResponse;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.service.MemberFindService;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class GroupSearchService {

    private static final int DEFAULT_PAGE_SIZE = 12;

    private final MemberFindService memberFindService;
    private final GroupFindService groupFindService;
    private final GroupSearchRepository groupSearchRepository;

    public GroupResponse findGroup(Long groupId, Long memberId) {
        Group group = groupFindService.findGroup(groupId);
        if (memberId == null) {
            return GroupResponseAssembler.groupResponseWithoutLogin(group);
        }
        Member member = memberFindService.findMember(memberId);
        return GroupResponseAssembler.groupResponseWithLogin(group, member);
    }

    public GroupPageResponse findGroups(GroupSearchRequest request, Long memberId) {
        Pageable pageable = PageRequest.of(request.getPage(), DEFAULT_PAGE_SIZE);
        Page<Group> groups = groupSearchRepository.findGroups(request.toFindCondition(), pageable);
        List<Group> groupsOfPage = groups.getContent();
        List<GroupSummaryResponse> summaries = getGroupSummaryResponses(groupsOfPage, memberId);

        return GroupResponseAssembler.groupPageResponse(summaries, groups.hasNext(), request.getPage());
    }

    private List<GroupSummaryResponse> getGroupSummaryResponses(List<Group> groupsOfPage, Long memberId) {
        if (memberId == null) {
            return GroupResponseAssembler.groupSummaryResponsesWithoutLogin(groupsOfPage);
        }

        Member member = memberFindService.findMember(memberId);
        return GroupResponseAssembler.groupSummaryResponsesWithLogin(groupsOfPage, member);
    }

    public GroupPageResponse findParticipatedGroups(GroupSearchRequest request, Long memberId) {
        Pageable pageable = PageRequest.of(request.getPage(), DEFAULT_PAGE_SIZE);
        Member member = memberFindService.findMember(memberId);
        Page<Group> groups = groupSearchRepository.findParticipatedGroups(request.toFindCondition(), member, pageable);
        List<Group> groupsOfPage = groups.getContent();
        List<GroupSummaryResponse> summaries = GroupResponseAssembler.groupSummaryResponsesWithLogin(groupsOfPage,
                member);

        return GroupResponseAssembler.groupPageResponse(summaries, groups.hasNext(), request.getPage());
    }

    public GroupPageResponse findHostedGroups(GroupSearchRequest request, Long memberId) {
        Pageable pageable = PageRequest.of(request.getPage(), DEFAULT_PAGE_SIZE);
        Member member = memberFindService.findMember(memberId);
        Page<Group> groups = groupSearchRepository.findHostedGroups(request.toFindCondition(), member, pageable);
        List<Group> groupsOfPage = groups.getContent();
        List<GroupSummaryResponse> summaries = GroupResponseAssembler.groupSummaryResponsesWithLogin(groupsOfPage,
                member);

        return GroupResponseAssembler.groupPageResponse(summaries, groups.hasNext(), request.getPage());
    }

    public GroupPageResponse findLikedGroups(GroupSearchRequest request, Long memberId) {
        Pageable pageable = PageRequest.of(request.getPage(), DEFAULT_PAGE_SIZE);
        Member member = memberFindService.findMember(memberId);
        Page<Group> groups = groupSearchRepository.findLikedGroups(request.toFindCondition(), member, pageable);
        List<Group> groupsOfPage = groups.getContent();
        List<GroupSummaryResponse> summaries = GroupResponseAssembler.groupSummaryResponsesWithLogin(groupsOfPage,
                member);

        return GroupResponseAssembler.groupPageResponse(summaries, groups.hasNext(), request.getPage());
    }
}
