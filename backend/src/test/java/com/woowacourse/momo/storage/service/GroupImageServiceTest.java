package com.woowacourse.momo.storage.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import static com.woowacourse.momo.fixture.calendar.DurationFixture.이틀후부터_5일동안;
import static com.woowacourse.momo.fixture.calendar.ScheduleFixture.이틀후_10시부터_12시까지;
import static com.woowacourse.momo.fixture.calendar.datetime.DateTimeFixture.내일_23시_59분;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.momo.auth.support.SHA256Encoder;
import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.group.domain.calendar.Deadline;
import com.woowacourse.momo.group.domain.calendar.Schedules;
import com.woowacourse.momo.group.domain.group.Capacity;
import com.woowacourse.momo.group.domain.group.Group;
import com.woowacourse.momo.group.domain.group.GroupName;
import com.woowacourse.momo.group.domain.group.GroupRepository;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;
import com.woowacourse.momo.member.domain.Password;
import com.woowacourse.momo.member.domain.UserId;

@Transactional
@SpringBootTest
class GroupImageServiceTest {

    @Autowired
    private GroupImageService groupImageService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private GroupRepository groupRepository;

    private Password password;
    private Member savedHost;
    private Group savedGroup;

    @BeforeEach
    void setUp() {
        password = Password.encrypt("momo123!", new SHA256Encoder());
        savedHost = memberRepository.save(new Member(UserId.momo("주최자"), password, "momo"));
        savedGroup = saveGroup("모모의 그룹", Category.CAFE);
    }

    @DisplayName("이미지 정보를 저장한다")
    @Test
    void save() {
        MockMultipartFile image = new MockMultipartFile(
                "imageFile",
                "asdf.png",
                MediaType.IMAGE_PNG_VALUE,
                "asdf".getBytes()
        );
        assertDoesNotThrow(() -> groupImageService.save(savedGroup.getId(), image));
    }

    private Group saveGroup(String name, Category category) {
        return groupRepository.save(new Group(new GroupName(name), savedHost, category, new Capacity(3),
                이틀후부터_5일동안.toDuration(), new Deadline(내일_23시_59분.toDateTime()),
                new Schedules(List.of(이틀후_10시부터_12시까지.toSchedule())), "", ""));
    }
}
