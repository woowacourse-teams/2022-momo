package com.woowacourse.momo.favorite.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    boolean existsByGroupIdAndMemberId(Long groupId, Long memberId);

    @Query("SELECT f FROM Favorite f "
            + "JOIN FETCH f.member m "
            + "WHERE f.id = :groupId AND m.id = :memberId")
    Optional<Favorite> findByGroupIdAndMemberId(Long groupId, Long memberId);

    @Query("SELECT f FROM Favorite f "
            + "WHERE f.member.id = :memberId")
    List<Favorite> findAllByMemberId(Long memberId);
}
