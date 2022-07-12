package com.woowacourse.momo.group.domain.group;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.woowacourse.momo.group.domain.duration.Duration;
import com.woowacourse.momo.group.domain.schedule.Schedule;

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

    @OneToMany(mappedBy = "group",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true)
    @MapKeyColumn(name = "date")
    private Map<LocalDate, Schedule> schedules = new HashMap<>();

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
        this.location = location;
        this.description = description;

        belongTo(schedules);
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
        belongTo(schedules);
    }

    private void belongTo(List<Schedule> schedules) {
        for (Schedule schedule : schedules) {
            this.schedules.put(schedule.getDate(), schedule);
            schedule.belongTo(this);
        }
    }

    public List<Schedule> getSchedules() {
        return new ArrayList<>(schedules.values());
    }
}
