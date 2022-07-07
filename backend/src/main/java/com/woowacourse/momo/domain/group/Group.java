package com.woowacourse.momo.domain.group;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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
    private boolean regular;

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

    public Group(String name, Long hostId, Long categoryId, boolean regular, Duration duration, LocalDateTime deadline,
                 List<Schedule> schedules, String location, String description) {
        this.name = name;
        this.hostId = hostId;
        this.categoryId = categoryId;
        this.regular = regular;
        this.duration = duration;
        this.deadline = deadline;
        this.schedules = new Schedules(schedules, this);
        this.location = location;
        this.description = description;
    }
}
