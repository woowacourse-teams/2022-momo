package com.woowacourse.momo.domain.group;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @Enumerated(EnumType.STRING)
    private Day reservationDay;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    public Schedule(Day reservationDay, LocalTime startTime, LocalTime endTime) {
        this.reservationDay = reservationDay;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void appendToGroup(Group group) {
        this.group = group;
        group.appendSchedule(this);
    }
}
