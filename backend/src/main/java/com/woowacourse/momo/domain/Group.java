package com.woowacourse.momo.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
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

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
    private List<Schedule> schedules;

    @Column(nullable = false)
    private String location;

    @Lob
    @Column(nullable = false)
    private String description;
}
