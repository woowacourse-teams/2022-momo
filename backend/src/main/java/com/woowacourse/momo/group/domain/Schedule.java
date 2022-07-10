package com.woowacourse.momo.group.domain;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;

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

    private Schedule(Day reservationDay, LocalTime startTime, LocalTime endTime) {
        this.reservationDay = reservationDay;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static Schedule of(String reservationDay, String startTime, String endTime) {
        return new Schedule(Day.from(reservationDay),
                LocalTime.parse(startTime, DateTimeFormatter.ISO_LOCAL_TIME),
                LocalTime.parse(endTime, DateTimeFormatter.ISO_LOCAL_TIME));
    }

    public static Schedule of(String reservationDay, LocalTime startTime, LocalTime endTime) {
        return new Schedule(Day.from(reservationDay), startTime, endTime);
    }

    public void belongTo(Group group) {
        this.group = group;
    }
}
