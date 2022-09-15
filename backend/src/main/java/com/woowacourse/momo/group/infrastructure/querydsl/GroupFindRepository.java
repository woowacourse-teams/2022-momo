package com.woowacourse.momo.group.infrastructure.querydsl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;

import com.woowacourse.momo.group.domain.Group;

public interface GroupFindRepository extends Repository<Group, Long>, GroupFindRepositoryCustom {

    Optional<Group> findById(Long id);

    List<Group> findAll();
}
