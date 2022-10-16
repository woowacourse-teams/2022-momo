package com.woowacourse.momo.group.domain.calendar;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface ScheduleRepository extends Repository<Schedule, Long> {

    @Modifying
    @Query("delete from Schedule s where s.group.id = :groupId")
    void deleteAllByGroupId(@Param("groupId") Long groupId);

    @Modifying
    @Query("delete from Schedule s where s in (:schedules)")
    void deleteAllInSchedules(@Param("schedules") List<Schedule> schedules);
}
