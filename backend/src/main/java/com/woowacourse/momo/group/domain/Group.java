package com.woowacourse.momo.group.domain;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long hostId;

    @Column(nullable = false)
    private Long categoryId;

    @Column(nullable = false)
    @Embedded
    private Duration duration;

    @Column(nullable = false)
    private LocalDateTime deadline;

    @Embedded
    private Schedules schedules;

    @Column(nullable = false)
    private String location;

    @Lob
    @Column(nullable = false)
    private String description;

    public Group(String name, Long hostId, Long categoryId, Duration duration, LocalDateTime deadline,
                 List<Schedule> schedules, String location, String description) {
        this.name = name;
        this.hostId = hostId;
        this.categoryId = categoryId;
        this.duration = duration;
        this.deadline = deadline;
        this.schedules = new Schedules(schedules, this);
        this.location = location;
        this.description = description;
    }

    public void update(String name, Long categoryId, Duration duration, LocalDateTime deadline,
                 List<Schedule> schedules, String location, String description) {
        this.name = name;
        this.categoryId = categoryId;
        this.duration = duration;
        this.deadline = deadline;
        this.location = location;
        this.description = description;

        this.schedules.clear();
        schedules.forEach(schedule -> this.schedules.add(this, schedule));
    }
}
