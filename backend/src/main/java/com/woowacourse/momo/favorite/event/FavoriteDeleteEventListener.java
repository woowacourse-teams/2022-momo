package com.woowacourse.momo.favorite.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.favorite.domain.FavoriteRepository;
import com.woowacourse.momo.group.event.GroupDeleteEvent;
import com.woowacourse.momo.member.event.MemberDeleteEvent;

@RequiredArgsConstructor
@Component
public class FavoriteDeleteEventListener {

    private final FavoriteRepository favoriteRepository;

    @EventListener
    public void deleteGroup(GroupDeleteEvent event) {
        favoriteRepository.deleteAllByGroupId(event.getId());
    }

    @EventListener
    public void deleteMember(MemberDeleteEvent event) {
        favoriteRepository.deleteAllByMemberId(event.getId());
    }
}
