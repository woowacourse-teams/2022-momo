package com.woowacourse.momo.domain.group;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedules {

    @OneToMany(mappedBy = "group",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true)
    private List<Schedule> value = new ArrayList<>();

    public Schedules(List<Schedule> value) {
        this.value = value;
    }

    public Schedules(List<Schedule> value, Group group) {
        this(value);
        belongTo(group);
    }

    private void belongTo(Group group) {
        value.forEach(v -> v.belongTo(group));
    }

    public void clear() {
        value.clear();
    }

    public void add(Group group, Schedule schedule) {
        this.value.add(schedule);
        schedule.belongTo(group);
    }
}
