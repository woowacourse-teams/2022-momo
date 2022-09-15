package com.woowacourse.momo.group.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import com.woowacourse.momo.group.repository.GroupRepositoryCustom;

public interface GroupRepository extends JpaRepository<Group, Long>, GroupRepositoryCustom {
}
