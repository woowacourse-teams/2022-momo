package com.woowacourse.momo.group.repository;

import com.woowacourse.momo.group.domain.Schedule;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ScheduleRepository extends Repository<Schedule, Long> {

    List<Schedule> findByGroupId(Long groupId);
}
