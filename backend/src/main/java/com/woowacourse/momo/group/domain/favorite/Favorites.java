package com.woowacourse.momo.group.domain.favorite;

import static com.woowacourse.momo.group.exception.GroupErrorCode.MEMBER_ALREADY_LIKE;
import static com.woowacourse.momo.group.exception.GroupErrorCode.MEMBER_NOT_YET_LIKE;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;

import com.woowacourse.momo.group.domain.Group;
import com.woowacourse.momo.group.exception.GroupException;
import com.woowacourse.momo.member.domain.Member;

@Getter
@NoArgsConstructor
@Embeddable
public class Favorites {

    @OneToMany(mappedBy = "group", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private final List<Favorite> favorites = new ArrayList<>();

    public void like(Group group, Member member) {
        validateMemberNotYetLike(member);
        Favorite favorite = new Favorite(group, member);
        favorites.add(favorite);
    }

    public void cancel(Member member) {
        validateMemberAlreadyLike(member);
        favorites.stream()
                .filter(favorite -> favorite.isSameMember(member))
                .findAny()
                .ifPresent(favorites::remove);
    }

    private void validateMemberNotYetLike(Member member) {
        if (hasMember(member)) {
            throw new GroupException(MEMBER_ALREADY_LIKE);
        }
    }

    private void validateMemberAlreadyLike(Member member) {
        if (!hasMember(member)) {
            throw new GroupException(MEMBER_NOT_YET_LIKE);
        }
    }

    public boolean hasMember(Member member) {
        return favorites.stream()
                .anyMatch(favorite -> favorite.isSameMember(member));
    }
}
