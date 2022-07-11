package com.woowacourse.momo.group.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.woowacourse.momo.group.domain.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
