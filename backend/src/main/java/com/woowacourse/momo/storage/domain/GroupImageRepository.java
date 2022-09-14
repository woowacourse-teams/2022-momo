package com.woowacourse.momo.storage.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import com.woowacourse.momo.group.domain.Group;

public interface GroupImageRepository extends JpaRepository<GroupImage, Long> {

    GroupImage findByGroup(Group group);
}
