package com.woowacourse.momo.domain.group;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Schedule> schedules = new ArrayList<>();

    @Column(nullable = false)
    private String location;

    @Lob
    @Column(nullable = false)
    private String description;

    public Group(Long hostId, Long categoryId, boolean regular, Duration duration, LocalDateTime deadline,
                 String location, String description) {
        this.hostId = hostId;
        this.categoryId = categoryId;
        this.regular = regular;
        this.duration = duration;
        this.deadline = deadline;
        this.location = location;
        this.description = description;
    }

    public void appendSchedule(Schedule schedule) {
        this.schedules.add(schedule);
    }
}
