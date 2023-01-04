package com.woowacourse.momo.favorite.event;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.favorite.domain.FavoriteRepository;
import com.woowacourse.momo.group.event.GroupDeleteEvent;
import com.woowacourse.momo.member.event.MemberDeleteEvent;

@RequiredArgsConstructor
@Transactional
@Component
public class FavoriteDeleteEventListener {

    private final FavoriteRepository favoriteRepository;

    @TransactionalEventListener
    @Async
    public void deleteGroup(GroupDeleteEvent event) {
        favoriteRepository.deleteAllByGroupId(event.getId());
    }

    @TransactionalEventListener
    @Async
    public void deleteMember(MemberDeleteEvent event) {
        favoriteRepository.deleteAllByMemberId(event.getId());
    }
}
