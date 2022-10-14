package com.woowacourse.momo.storage.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GroupImageRepository extends JpaRepository<GroupImage, Long> {

    Optional<GroupImage> findByGroupId(Long groupId);

    @Query("select gi.imageName from GroupImage gi where gi.groupId = :groupId")
    Optional<String> findImageNameByGroupId(@Param("groupId") Long groupId);

    void deleteByGroupId(Long groupId);
}
