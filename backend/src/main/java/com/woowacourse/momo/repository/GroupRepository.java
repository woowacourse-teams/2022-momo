package com.woowacourse.momo.repository;

import com.woowacourse.momo.domain.group.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
