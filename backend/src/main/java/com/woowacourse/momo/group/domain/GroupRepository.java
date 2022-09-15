package com.woowacourse.momo.group.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.woowacourse.momo.group.repository.GroupRepositoryCustom;

public interface GroupRepository extends JpaRepository<Group, Long>, JpaSpecificationExecutor<Group>,
        GroupRepositoryCustom {
}
