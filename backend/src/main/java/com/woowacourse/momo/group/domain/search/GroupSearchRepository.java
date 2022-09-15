package com.woowacourse.momo.group.domain.search;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;

import com.woowacourse.momo.group.domain.Group;

public interface GroupSearchRepository extends Repository<Group, Long>, GroupSearchRepositoryCustom {

    Optional<Group> findById(Long id);

    List<Group> findAll();
}
