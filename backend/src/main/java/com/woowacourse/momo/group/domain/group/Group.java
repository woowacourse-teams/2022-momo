package com.woowacourse.momo.group.domain.group;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.group.domain.duration.Duration;
import com.woowacourse.momo.group.domain.schedule.Schedule;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.participant.domain.Participant;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private int maxOfParticipants;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Participant> participants = new ArrayList<>();

    @Column(nullable = false)
    @Embedded
    private Duration duration;

    @Column(nullable = false)
    private LocalDateTime deadline;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "group_id")
    private List<Schedule> schedules = new ArrayList<>();

    @Column(nullable = false)
    private String location;

    @Lob
    @Column(nullable = false)
    private String description;

    public Group(String name, Long hostId, Category category, int maxOfParticipants, Duration duration,
                 LocalDateTime deadline, List<Schedule> schedules, String location, String description) {
        this.name = name;
        this.hostId = hostId;
        this.category = category;
        this.maxOfParticipants = maxOfParticipants;
        this.duration = duration;
        this.deadline = deadline;
        this.location = location;
        this.description = description;

        belongTo(schedules);
    }

    public void update(String name, Category category, int maxOfParticipants, Duration duration, LocalDateTime deadline,
                       List<Schedule> schedules, String location, String description) {
        this.name = name;
        this.category = category;
        this.maxOfParticipants = maxOfParticipants;
        this.duration = duration;
        this.deadline = deadline;
        this.location = location;
        this.description = description;

        this.schedules.clear();
        belongTo(schedules);
    }

    public boolean isSameHost(Member host) {
        return Objects.equals(this.hostId, host.getId());
    }

    public void participate(Member member) {
        this.participants.add(new Participant(this, member));
    }

    private void belongTo(List<Schedule> schedules) {
        this.schedules.addAll(schedules);
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public List<Member> getParticipants() {
        return participants.stream()
                .map(Participant::getMember)
                .collect(Collectors.toList());
    }

    public static class Builder {

        private String name;
        private Long hostId;
        private Category category;
        private int maxOfParticipants;
        private Duration duration;
        private LocalDateTime deadline;
        private List<Schedule> schedules;
        private String location;
        private String description;

        public Builder() {
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder hostId(Long hostId) {
            this.hostId = hostId;
            return this;
        }

        public Builder category(Category category) {
            this.category = category;
            return this;
        }

        public Builder categoryId(long categoryId) {
            this.category = Category.from(categoryId);
            return this;
        }

        public Builder maxOfParticipants(int maxOfParticipants) {
            this.maxOfParticipants = maxOfParticipants;
            return this;
        }

        public Builder duration(Duration duration) {
            this.duration = duration;
            return this;
        }

        public Builder deadline(LocalDateTime deadline) {
            this.deadline = deadline;
            return this;
        }

        public Builder schedules(List<Schedule> schedules) {
            this.schedules = schedules;
            return this;
        }

        public Builder location(String location) {
            this.location = location;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Group build() {
            validateNonNull();
            return new Group(name, hostId, category, maxOfParticipants, duration, deadline, schedules, location, description);
        }

        private void validateNonNull() {
            Objects.requireNonNull(name);
            Objects.requireNonNull(hostId);
            Objects.requireNonNull(category);
            Objects.requireNonNull(duration);
            Objects.requireNonNull(deadline);
            Objects.requireNonNull(schedules);
            Objects.requireNonNull(location);
            Objects.requireNonNull(description);
        }
    }
}
