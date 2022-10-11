package com.woowacourse.momo.group.domain.calendar;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface ScheduleRepository extends Repository<Schedule, Long> {

    @Modifying
    @Query("delete from Schedule where group.id = :groupId")
    void deleteAllByGroupId(@Param("groupId") Long groupId);
}
