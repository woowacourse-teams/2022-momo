package com.woowacourse.momo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.woowacourse.momo.domain.group.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
