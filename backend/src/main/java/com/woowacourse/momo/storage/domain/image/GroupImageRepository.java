package com.woowacourse.momo.storage.domain.image;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupImageRepository extends JpaRepository<GroupImage, Long> {
    Optional<GroupImage> findByGroupId(Long groupId);
}
