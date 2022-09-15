package com.woowacourse.momo.fixture;

import static com.woowacourse.momo.fixture.calendar.DeadlineFixture.내일_23시_59분까지;
import static com.woowacourse.momo.fixture.calendar.DurationFixture.이틀후_하루동안;
import static com.woowacourse.momo.fixture.calendar.DurationFixture.이틀후부터_5일동안;
import static com.woowacourse.momo.fixture.calendar.ScheduleFixture.toSchedules;
import static com.woowacourse.momo.fixture.calendar.ScheduleFixture.이틀후_10시부터_12시까지;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import com.woowacourse.momo.acceptance.group.GroupRestHandler;
import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.fixture.calendar.DeadlineFixture;
import com.woowacourse.momo.fixture.calendar.DurationFixture;
import com.woowacourse.momo.fixture.calendar.ScheduleFixture;
import com.woowacourse.momo.group.controller.dto.request.GroupApiRequest;
import com.woowacourse.momo.group.domain.Group;
import com.woowacourse.momo.group.domain.GroupName;
import com.woowacourse.momo.group.domain.calendar.Calendar;
import com.woowacourse.momo.group.domain.calendar.Deadline;
import com.woowacourse.momo.group.domain.participant.Capacity;
import com.woowacourse.momo.group.service.dto.request.GroupRequest;
import com.woowacourse.momo.member.domain.Member;

@SuppressWarnings("NonAsciiCharacters")
@Getter
public enum GroupFixture {

    MOMO_STUDY("모모의 스터디", Category.STUDY, 12, 이틀후부터_5일동안, List.of(이틀후_10시부터_12시까지),
            내일_23시_59분까지, "루터회관 13층", "같이 공부해요!!"),
    MOMO_TRAVEL("선릉 산책", Category.TRAVEL, 99, 이틀후_하루동안, List.of(이틀후_10시부터_12시까지),
            내일_23시_59분까지, "선릉", "점심 먹고 선릉 나들이~!!"),
    DUDU_STUDY("두두와의 스터디", Category.STUDY, 8, 이틀후부터_5일동안, List.of(이틀후_10시부터_12시까지),
            내일_23시_59분까지, "루터회관 13층", "두두랑 함께 공부해요!!"),
    DUDU_COFFEE_TIME("두두와의 커피타임", Category.CAFE, 2, 이틀후_하루동안, List.of(이틀후_10시부터_12시까지),
            내일_23시_59분까지, "잠실역 스타벅스", "두두가 쏘는 커피~ 선착순 1명!!");

    private final GroupName name;
    private final Category category;
    private final Capacity capacity;
    private final DurationFixture duration;
    private final List<ScheduleFixture> schedules;
    private final DeadlineFixture deadline;
    private final String location;
    private final String description;

    GroupFixture(String name, Category category, Integer capacity, DurationFixture duration,
                 List<ScheduleFixture> schedules, DeadlineFixture deadline, String location, String description) {
        this.name = new GroupName(name);
        this.category = category;
        this.capacity = new Capacity(capacity);
        this.duration = duration;
        this.schedules = schedules;
        this.deadline = deadline;
        this.location = location;
        this.description = description;
    }

    public Long 을_생성한다(String accessToken) {
        return GroupRestHandler.모임을_생성한다(accessToken, this)
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .jsonPath()
                .getLong("groupId");
    }

    public long getCategoryId() {
        return category.getId();
    }

    public Calendar getCalendar() {
        return new Calendar(deadline.toDeadline(), duration.toDuration(), toSchedules(schedules));
    }

    public static void setDeadlinePast(Group group, int pastDays) {
        LocalDateTime now = LocalDateTime.now();
        Calendar calendar = new Calendar(new Deadline(now.plusHours(1)), group.getDuration(), group.getSchedules());

        try {
            Field fieldDeadline = Calendar.class.getDeclaredField("deadline");
            fieldDeadline.setAccessible(true);
            fieldDeadline.set(calendar, DeadlineFixture.newDeadline(-pastDays));

            Field fieldCalendar = Group.class.getDeclaredField("calendar");
            fieldCalendar.setAccessible(true);
            fieldCalendar.set(group, calendar);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("해당하는 필드를 찾을 수 없습니다.");
        }
    }

    public Builder builder() {
        return new Builder(this);
    }

    public Group toGroup(Member host) {
        return builder().toGroup(host);
    }

    public GroupRequest toRequest() {
        return builder().toRequest();
    }

    public GroupApiRequest toApiRequest() {
        return builder().toApiRequest();
    }

    public static class Builder {

        private String name;
        private long category;
        private int capacity;
        private DurationFixture duration;
        private List<ScheduleFixture> schedules;
        private DeadlineFixture deadline;
        private String location;
        private String description;

        private Builder(GroupFixture group) {
            this.name = group.getName().getValue();
            this.category = group.getCategory().getId();
            this.capacity = group.getCapacity().getValue();
            this.duration = group.getDuration();
            this.schedules = group.getSchedules();
            this.deadline = group.getDeadline();
            this.location = group.getLocation();
            this.description = group.getDescription();
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder category(long categoryId) {
            this.category = categoryId;
            return this;
        }

        public Builder category(Category category) {
            return category(category.getId());
        }

        public Builder capacity(int capacity) {
            this.capacity = capacity;
            return this;
        }

        public Builder capacity(Capacity capacity) {
            return capacity(capacity.getValue());
        }

        public Builder schedules(List<ScheduleFixture> schedules) {
            this.schedules = schedules;
            return this;
        }

        public Builder schedules(ScheduleFixture schedules) {
            return schedules(List.of(schedules));
        }

        public Builder deadline(DeadlineFixture deadline) {
            this.deadline = deadline;
            return this;
        }

        public Builder duration(DurationFixture duration) {
            this.duration = duration;
            return this;
        }

        public Group toGroup(Member host) {
            Calendar calendar = new Calendar(deadline.toDeadline(), duration.toDuration(), toSchedules(schedules));
            return new Group(host, new Capacity(capacity), calendar, new GroupName(name), Category.from(category),
                    location, description);
        }

        public GroupRequest toRequest() {
            return new GroupRequest(name, category, capacity, duration.toRequest(),
                    ScheduleFixture.toRequests(schedules), deadline.toRequest(), location, description);
        }

        public GroupApiRequest toApiRequest() {
            return new GroupApiRequest(name, category, capacity, duration.toApiRequest(),
                    ScheduleFixture.toApiRequests(schedules), deadline.toApiRequest(),
                    location, description);
        }
    }
}
