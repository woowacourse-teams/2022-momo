package com.woowacourse.momo.favorite.service;

import static com.woowacourse.momo.group.exception.GroupErrorCode.MEMBER_ALREADY_LIKE;
import static com.woowacourse.momo.group.exception.GroupErrorCode.MEMBER_NOT_YET_LIKE;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.favorite.domain.Favorite;
import com.woowacourse.momo.favorite.domain.FavoriteRepository;
import com.woowacourse.momo.group.exception.GroupException;
import com.woowacourse.momo.group.service.GroupValidator;
import com.woowacourse.momo.member.service.MemberValidator;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class FavoriteService {

    private final GroupValidator groupValidator;
    private final MemberValidator memberValidator;
    private final FavoriteRepository favoriteRepository;

    @Transactional
    public void like(Long groupId, Long memberId) {
        groupValidator.validateExistGroup(groupId);
        memberValidator.validateExistMember(memberId);
        validateMemberNotYetLike(groupId, memberId);

        Favorite favorite = new Favorite(groupId, memberId);
        favoriteRepository.save(favorite);
    }

    private void validateMemberNotYetLike(Long groupId, Long memberId) {
        boolean isExist = favoriteRepository.existsByGroupIdAndMemberId(groupId, memberId);
        if (isExist) {
            throw new GroupException(MEMBER_ALREADY_LIKE);
        }
    }

    @Transactional
    public void cancel(Long groupId, Long memberId) {
        Favorite favorite = favoriteRepository.findByGroupIdAndMemberId(groupId, memberId)
                .orElseThrow(() -> new GroupException(MEMBER_NOT_YET_LIKE));

        favoriteRepository.delete(favorite);
    }
}
