package com.woowacourse.momo.favorite.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface FavoriteRepository extends Repository<Favorite, Long> {

    Favorite save(Favorite favorite);

    boolean existsByGroupIdAndMemberId(Long groupId, Long memberId);

    Optional<Favorite> findByGroupIdAndMemberId(Long groupId, Long memberId);

    List<Favorite> findAllByMemberId(Long memberId);

    void delete(Favorite favorite);

    @Modifying
    @Query("delete from Favorite f where f.groupId = :groupId")
    void deleteAllByGroupId(@Param("groupId") Long groupId);

    @Modifying
    @Query("delete from Favorite f where f.memberId = :memberId")
    void deleteAllByMemberId(@Param("memberId") Long memberId);
}
