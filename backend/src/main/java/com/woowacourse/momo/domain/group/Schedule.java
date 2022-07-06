package com.woowacourse.momo.domain.group;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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

    public static Schedule of(Day reservationDay, String startTime, String endTime) {
        return new Schedule(reservationDay,
                LocalTime.parse(startTime, DateTimeFormatter.ISO_LOCAL_TIME),
                LocalTime.parse(endTime, DateTimeFormatter.ISO_LOCAL_TIME));
    }

    public void belongTo(Group group) {
        this.group = group;
    }
}
