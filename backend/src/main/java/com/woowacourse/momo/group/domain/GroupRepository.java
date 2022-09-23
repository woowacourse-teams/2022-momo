package com.woowacourse.momo.group.domain;

import org.springframework.data.repository.Repository;

public interface GroupRepository extends Repository<Group, Long> {

    Group save(Group group);

    void deleteById(Long id);
}
