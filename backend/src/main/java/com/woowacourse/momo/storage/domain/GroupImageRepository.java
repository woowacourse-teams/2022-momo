package com.woowacourse.momo.storage.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.woowacourse.momo.group.domain.Group;

public interface GroupImageRepository extends JpaRepository<GroupImage, Long> {

    Optional<GroupImage> findByGroup(Group group);

    void deleteByGroup(Group group);
}
