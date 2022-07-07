package com.woowacourse.momo.domain.group;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

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
}
