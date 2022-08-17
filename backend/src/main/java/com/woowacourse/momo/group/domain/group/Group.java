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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.global.exception.exception.ErrorCode;
import com.woowacourse.momo.global.exception.exception.MomoException;
import com.woowacourse.momo.group.domain.calendar.Deadline;
import com.woowacourse.momo.group.domain.calendar.Duration;
import com.woowacourse.momo.group.domain.calendar.Schedule;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.participant.domain.Participant;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Group {

    private static final int NONE_PARTICIPANT = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn
    private Member host;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Embedded
    private Capacity capacity;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Participant> participants = new ArrayList<>();

    @Embedded
    private Duration duration;

    @Embedded
    private Deadline deadline;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "group_id")
    private List<Schedule> schedules = new ArrayList<>();

    @Column(nullable = false)
    private String location;

    @Lob
    @Column(nullable = false)
    private String description;

    private boolean earlyClosed;

    public Group(String name, Member host, Category category, int capacity, Duration duration,
                 LocalDateTime deadline, List<Schedule> schedules, String location, String description) {
        this.name = name;
        this.host = host;
        this.category = category;
        this.capacity = new Capacity(capacity);
        this.duration = duration;
        this.deadline = new Deadline(deadline, this.duration);
        this.location = location;
        this.description = description;

        this.participants.add(new Participant(this, host));
        belongTo(schedules);
    }

    public void update(String name, Category category, int capacity, Duration duration, LocalDateTime deadline,
                       List<Schedule> schedules, String location, String description) {
        this.name = name;
        this.category = category;
        this.capacity = new Capacity(capacity);
        this.duration = duration;
        this.deadline = new Deadline(deadline, this.duration);
        this.location = location;
        this.description = description;

        this.schedules.clear();
        belongTo(schedules);
    }

    public void participate(Member member) {
        validateParticipateAvailable(member);
        this.participants.add(new Participant(this, member));
    }

    public void closeEarly() {
        this.earlyClosed = true;
    }

    private void belongTo(List<Schedule> schedules) {
        this.schedules.addAll(schedules);
    }

    public boolean isExistParticipants() {
        return participants.size() > NONE_PARTICIPANT;
    }

    public boolean isHost(Member host) {
        return this.host.equals(host);
    }

    public boolean isEnd() {
        return earlyClosed || isOverDeadline();
    }

    public boolean isFinishedRecruitment() {
        return earlyClosed || isFullCapacity() || isOverDeadline();
    }

    private boolean isOverDeadline() {
        return deadline.isOver();
    }

    private boolean isFullCapacity() {
        return capacity.isFull(participants.size());
    }

    private boolean isParticipant(Member member) {
        return getParticipants().contains(member);
    }

    private void validateParticipateAvailable(Member member) {
        validateFinishedRecruitment();
        validateReParticipate(member);
        validateIsHost(member);
    }

    private void validateIsHost(Member member) {
        if (isHost(member)) {
            throw new MomoException(ErrorCode.PARTICIPANT_JOIN_BY_HOST);
        }
    }

    private void validateFinishedRecruitment() {
        if (isFinishedRecruitment()) {
            throw new MomoException(ErrorCode.PARTICIPANT_FINISHED);
        }
    }

    private void validateReParticipate(Member member) {
        if (getParticipants().contains(member)) {
            throw new MomoException(ErrorCode.PARTICIPANT_RE_PARTICIPATE);
        }
    }

    public void validateLeave(Member member) {
        if (isHost(member)) {
            throw new MomoException(ErrorCode.PARTICIPANT_LEAVE_HOST);
        }
        if (!isParticipant(member)) {
            throw new MomoException(ErrorCode.PARTICIPANT_LEAVE_NOT_PARTICIPANT);
        }
        if (isOverDeadline()) {
            throw new MomoException(ErrorCode.PARTICIPANT_LEAVE_DEADLINE);
        }
        if (earlyClosed) {
            throw new MomoException(ErrorCode.PARTICIPANT_LEAVE_EARLY_CLOSED);
        }
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public List<Member> getParticipants() {
        return participants.stream()
                .map(Participant::getMember)
                .collect(Collectors.toList());
    }

    public int getCapacity() {
        return capacity.getValue();
    }

    public LocalDateTime getDeadline() {
        return deadline.getValue();
    }

    public static class Builder {

        private String name;
        private Member host;
        private Category category;
        private int capacity;
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

        public Builder host(Member host) {
            this.host = host;
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

        public Builder capacity(int capacity) {
            this.capacity = capacity;
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
            return new Group(name, host, category, capacity, duration, deadline, schedules, location,
                    description);
        }

        private void validateNonNull() {
            Objects.requireNonNull(name);
            Objects.requireNonNull(host);
            Objects.requireNonNull(category);
            Objects.requireNonNull(duration);
            Objects.requireNonNull(deadline);
            Objects.requireNonNull(schedules);
            Objects.requireNonNull(location);
            Objects.requireNonNull(description);
        }
    }
}
