package com.woowacourse.momo.group.domain.search;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.woowacourse.momo.group.domain.Group;

public interface GroupSearchRepository extends Repository<Group, Long>, GroupSearchRepositoryCustom {

    Optional<Group> findById(Long id);

    @Query("SELECT g FROM Group g "
            + "JOIN FETCH g.calendar.schedules.value "
            + "JOIN FETCH g.participants.host "
            + "WHERE g.id = :id")
    Optional<Group> findByIdWithHostAndSchedule(Long id);

    boolean existsById(Long id);
}
