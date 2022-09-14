package com.woowacourse.momo.storage.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import static com.woowacourse.momo.fixture.calendar.DurationFixture.이틀후부터_5일동안;
import static com.woowacourse.momo.fixture.calendar.ScheduleFixture.이틀후_10시부터_12시까지;
import static com.woowacourse.momo.fixture.calendar.datetime.DateTimeFixture.내일_23시_59분;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.momo.auth.support.SHA256Encoder;
import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.global.exception.exception.MomoException;
import com.woowacourse.momo.group.domain.Group;
import com.woowacourse.momo.group.domain.GroupName;
import com.woowacourse.momo.group.domain.GroupRepository;
import com.woowacourse.momo.group.domain.calendar.Calendar;
import com.woowacourse.momo.group.domain.calendar.Deadline;
import com.woowacourse.momo.group.domain.calendar.Schedules;
import com.woowacourse.momo.group.domain.participant.Capacity;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;
import com.woowacourse.momo.member.domain.Password;
import com.woowacourse.momo.member.domain.UserId;
import com.woowacourse.momo.storage.domain.GroupImage;
import com.woowacourse.momo.storage.domain.GroupImageRepository;

@Transactional
@SpringBootTest
class GroupImageServiceTest {

    @Autowired
    private GroupImageService groupImageService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupImageRepository groupImageRepository;

    @MockBean
    private StorageService storageService;

    public static final MockMultipartFile IMAGE = new MockMultipartFile(
            "imageFile",
            "asdf.png",
            MediaType.IMAGE_PNG_VALUE,
            "asdf".getBytes()
    );

    private Password password;
    private Member savedHost;
    private Group savedGroup;

    @BeforeEach
    void setUp() {
        Mockito.when(storageService.save(Mockito.anyString(), Mockito.any()))
                .thenReturn("abc.jpg");

        password = Password.encrypt("momo123!", new SHA256Encoder());
        savedHost = memberRepository.save(new Member(UserId.momo("주최자"), password, "momo"));
        savedGroup = saveGroup("모모의 그룹", Category.CAFE);
    }

    @DisplayName("기본 이미지 정보를 저장한다")
    @Test
    void init() {
        groupImageService.init(savedGroup);

        String imageName = savedGroup.getCategory().getDefaultImageName();
        GroupImage groupImage = groupImageRepository.findByGroup(savedGroup);

        assertAll(
                () -> assertThat(groupImage.getGroup()).isEqualTo(savedGroup),
                () -> assertThat(groupImage.getImageName()).isEqualTo(imageName)
        );
    }

    @DisplayName("이미지 정보를 저장한다")
    @Test
    void save() {
        assertDoesNotThrow(() -> groupImageService.save(savedGroup.getHost().getId(), savedGroup.getId(), IMAGE));
    }

    @DisplayName("이미지를 저장할 때 주최자가 아니면 예외가 발생한다")
    @Test
    void saveMemberIsNotHost() {
        Member member = memberRepository.save(new Member(UserId.momo("사용자"), password, "momo"));

        assertThatThrownBy(() -> groupImageService.save(member.getId(), savedGroup.getId(), IMAGE))
                .isInstanceOf(MomoException.class)
                .hasMessage("모임의 주최자가 아닙니다.");
    }

    private Group saveGroup(String name, Category category) {
        Calendar calendar = new Calendar(
                new Deadline(내일_23시_59분.toDateTime()), 이틀후부터_5일동안.toDuration(),
                new Schedules(List.of(이틀후_10시부터_12시까지.toSchedule()))
        );
        Group group = new Group(
                savedHost, new Capacity(3), calendar, new GroupName(name), category, "", ""
        );
        return groupRepository.save(group);
    }
}
