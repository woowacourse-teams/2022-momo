package com.woowacourse.momo.group.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.woowacourse.momo.group.domain.Schedule;

public interface ScheduleRepository extends Repository<Schedule, Long> {

    List<Schedule> findByGroupId(Long groupId);
}
