package com.woowacourse.momo.favorite.service;

import static com.woowacourse.momo.group.exception.GroupErrorCode.MEMBER_ALREADY_LIKE;
import static com.woowacourse.momo.group.exception.GroupErrorCode.MEMBER_NOT_YET_LIKE;
import static com.woowacourse.momo.group.exception.GroupErrorCode.NOT_EXIST;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.favorite.domain.Favorite;
import com.woowacourse.momo.favorite.domain.FavoriteRepository;
import com.woowacourse.momo.group.domain.search.GroupSearchRepository;
import com.woowacourse.momo.group.exception.GroupException;
import com.woowacourse.momo.member.domain.MemberRepository;
import com.woowacourse.momo.member.exception.MemberErrorCode;
import com.woowacourse.momo.member.exception.MemberException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class FavoriteService {

    private final GroupSearchRepository groupSearchRepository;
    private final MemberRepository memberRepository;
    private final FavoriteRepository favoriteRepository;

    @Transactional
    public void like(Long groupId, Long memberId) {
        validateExistGroup(groupId);
        validateExistMember(memberId);
        validateMemberNotYetLike(groupId, memberId);

        Favorite favorite = new Favorite(groupId, memberId);
        favoriteRepository.save(favorite);
    }

    private void validateExistGroup(Long groupId) {
        boolean result = groupSearchRepository.existsById(groupId);
        if (!result) {
            throw new GroupException(NOT_EXIST);
        }
    }

    private void validateExistMember(Long memberId) {
        boolean result = memberRepository.existsById(memberId);
        if (!result) {
            throw new MemberException(MemberErrorCode.MEMBER_NOT_EXIST);
        }
    }

    private void validateMemberNotYetLike(Long groupId, Long memberId) {
        boolean result = favoriteRepository.existsByGroupIdAndMemberId(groupId, memberId);
        if (result) {
            throw new GroupException(MEMBER_ALREADY_LIKE);
        }
    }

    @Transactional
    public void cancel(Long groupId, Long memberId) {
        Optional<Favorite> favorite = favoriteRepository.findByGroupIdAndMemberId(groupId, memberId);
        if (favorite.isEmpty()) {
            throw new GroupException(MEMBER_NOT_YET_LIKE);
        }

        favoriteRepository.delete(favorite.get());
    }
}
