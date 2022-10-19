package com.woowacourse.momo.group.service;

import java.util.List;
import java.util.function.BiFunction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.favorite.domain.Favorite;
import com.woowacourse.momo.favorite.domain.FavoriteRepository;
import com.woowacourse.momo.group.domain.Group;
import com.woowacourse.momo.group.domain.search.GroupSearchRepository;
import com.woowacourse.momo.group.domain.search.SearchCondition;
import com.woowacourse.momo.group.domain.search.dto.GroupSummaryRepositoryResponse;
import com.woowacourse.momo.group.service.dto.request.GroupSearchRequest;
import com.woowacourse.momo.group.service.dto.response.GroupPageResponse;
import com.woowacourse.momo.group.service.dto.response.GroupResponse;
import com.woowacourse.momo.group.service.dto.response.GroupResponseAssembler;
import com.woowacourse.momo.group.service.dto.response.GroupSummaryResponse;
import com.woowacourse.momo.member.service.MemberValidator;
import com.woowacourse.momo.storage.domain.GroupImage;
import com.woowacourse.momo.storage.domain.GroupImageRepository;
import com.woowacourse.momo.storage.support.ImageProvider;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class GroupSearchService {

    private static final int DEFAULT_PAGE_SIZE = 12;

    private final MemberValidator memberValidator;
    private final GroupFindService groupFindService;
    private final GroupSearchRepository groupSearchRepository;
    private final FavoriteRepository favoriteRepository;
    private final GroupImageRepository groupImageRepository;
    private final ImageProvider imageProvider;

    public GroupResponse findGroup(Long groupId) {
        Group group = groupFindService.findByIdWithHostAndSchedule(groupId);
        String imageUrl = getImageUrl(group);
        return GroupResponseAssembler.groupResponse(group, imageUrl);
    }

    private String getImageUrl(Group group) {
        String imageName = groupImageRepository.findByGroupId(group.getId())
                .map(GroupImage::getImageName)
                .orElse(group.getCategory().getDefaultImageName());
        return imageProvider.generateGroupImageUrl(imageName, group.getCategory().isDefaultImage(imageName));
    }

    public GroupResponse findGroup(Long groupId, Long memberId) {
        memberValidator.validateExistMember(memberId);
        boolean favoriteChecked = favoriteRepository.existsByGroupIdAndMemberId(groupId, memberId);

        Group group = groupFindService.findByIdWithHostAndSchedule(groupId);
        String imageUrl = getImageUrl(group);
        return GroupResponseAssembler.groupResponse(group, imageUrl, favoriteChecked);
    }

    public GroupPageResponse findGroups(GroupSearchRequest request) {
        SearchCondition searchCondition = request.toFindCondition();
        Pageable pageable = defaultPageable(request);
        Page<GroupSummaryRepositoryResponse> groups = groupSearchRepository.findGroups(searchCondition, pageable);

        List<GroupSummaryRepositoryResponse> groupsOfPage = convertImageUrl(groups);
        List<GroupSummaryResponse> summaries = GroupResponseAssembler.groupSummaryResponses(groupsOfPage);
        return GroupResponseAssembler.groupPageResponse(summaries, groups.hasNext(), request.getPage());
    }

    public GroupPageResponse findGroups(GroupSearchRequest request, Long memberId) {
        return findGroupsRelatedMember(request, memberId, groupSearchRepository::findGroups);
    }

    public GroupPageResponse findParticipatedGroups(GroupSearchRequest request, Long memberId) {
        return findGroupsRelatedMember(request, memberId, (searchCondition, pageable) ->
                groupSearchRepository.findParticipatedGroups(searchCondition, memberId, pageable));
    }

    public GroupPageResponse findHostedGroups(GroupSearchRequest request, Long memberId) {
        return findGroupsRelatedMember(request, memberId, (searchCondition, pageable) ->
                groupSearchRepository.findHostedGroups(searchCondition, memberId, pageable));
    }

    public GroupPageResponse findLikedGroups(GroupSearchRequest request, Long memberId) {
        return findGroupsRelatedMember(request, memberId, (searchCondition, pageable) ->
                groupSearchRepository.findLikedGroups(searchCondition, memberId, pageable));
    }

    private GroupPageResponse findGroupsRelatedMember(
            GroupSearchRequest request, Long memberId,
            BiFunction<SearchCondition, Pageable, Page<GroupSummaryRepositoryResponse>> function) {

        memberValidator.validateExistMember(memberId);
        List<Favorite> favorites = favoriteRepository.findAllByMemberId(memberId);

        SearchCondition searchCondition = request.toFindCondition();
        Pageable pageable = defaultPageable(request);
        Page<GroupSummaryRepositoryResponse> groups = function.apply(searchCondition, pageable);

        List<GroupSummaryRepositoryResponse> groupsOfPage = convertImageUrl(groups);
        List<GroupSummaryResponse> summaries = GroupResponseAssembler.groupSummaryResponses(groupsOfPage, favorites);
        return GroupResponseAssembler.groupPageResponse(summaries, groups.hasNext(), request.getPage());
    }

    private Pageable defaultPageable(GroupSearchRequest request) {
        return PageRequest.of(request.getPage(), DEFAULT_PAGE_SIZE);
    }

    private List<GroupSummaryRepositoryResponse> convertImageUrl(Page<GroupSummaryRepositoryResponse> repositoryResponses) {
        for (GroupSummaryRepositoryResponse repositoryResponse : repositoryResponses.getContent()) {
            String imageName = repositoryResponse.getImageName();
            String imageUrl = imageProvider.generateGroupImageUrl(imageName, repositoryResponse.getCategory().isDefaultImage(imageName));
            repositoryResponse.setImageName(imageUrl);
        }
        return repositoryResponses.getContent();
    }
}
