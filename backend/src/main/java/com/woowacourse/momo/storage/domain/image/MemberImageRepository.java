package com.woowacourse.momo.storage.domain.image;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberImageRepository extends JpaRepository<MemberImage, Long> {
    Optional<MemberImage> findByMemberId(Long id);
}
