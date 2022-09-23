package com.woowacourse.momo.group.domain.calendar;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString(includeFieldNames = false)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Schedules {

    @OneToMany(orphanRemoval = true, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "group_id")
    private final List<Schedule> value = new ArrayList<>();

    public Schedules(List<Schedule> schedules) {
        value.addAll(schedules);
    }

    public void change(Schedules schedules) {
        value.clear();
        value.addAll(schedules.value);
    }

    public boolean hasAnyScheduleOutOfDuration(Duration duration) {
        return value.stream()
                .anyMatch(schedule -> schedule.isOutOfDuration(duration));
    }
}
