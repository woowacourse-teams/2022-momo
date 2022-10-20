package com.woowacourse.momo.storage.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import static com.woowacourse.momo.fixture.GroupFixture.MOMO_STUDY;
import static com.woowacourse.momo.fixture.ImageFixture.PNG_IMAGE;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.momo.auth.support.SHA256Encoder;
import com.woowacourse.momo.global.exception.exception.MomoException;
import com.woowacourse.momo.group.domain.Group;
import com.woowacourse.momo.group.domain.GroupRepository;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;
import com.woowacourse.momo.member.domain.Password;
import com.woowacourse.momo.member.domain.UserId;
import com.woowacourse.momo.member.domain.UserName;
import com.woowacourse.momo.storage.domain.GroupImage;
import com.woowacourse.momo.storage.domain.GroupImageRepository;
import com.woowacourse.momo.storage.exception.GroupImageException;
import com.woowacourse.momo.storage.support.ImageConnector;

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
    private ImageConnector imageConnector;

    private static final MockMultipartFile IMAGE = PNG_IMAGE.toMultipartFile();

    private Password password;
    private Member savedHost;
    private Group savedGroup;

    @BeforeEach
    void setUp() {
        password = Password.encrypt("momo123!", new SHA256Encoder());
        savedHost = memberRepository.save(new Member(UserId.momo("host"), password, UserName.from("momo")));
        savedGroup = saveGroup();
    }

    @DisplayName("모임 이미지 정보를 수정한다")
    @Test
    void update() {
        String expected = "https://image.moyeora.site/group/saved/imageName.png";
        BDDMockito.given(imageConnector.requestImageSave(Mockito.anyString(), Mockito.any()))
                .willReturn(expected);

        GroupImage groupImage = new GroupImage(savedHost.getId(), savedGroup.getCategory().getDefaultImageName());
        groupImageRepository.save(groupImage);
        String actual = groupImageService.update(savedGroup.getHost().getId(), savedGroup.getId(), IMAGE);

        Optional<GroupImage> savedGroupImage = groupImageRepository.findByGroupId(savedGroup.getId());
        assertThat(savedGroupImage).isPresent();
        assertAll(
                () -> assertThat(actual).isEqualTo(expected),
                () -> assertThat(savedGroupImage.get().getImageName()).isEqualTo("imageName.png")
        );
    }

    @DisplayName("이전에 저장된 이미지가 존재하지 않을 때 모임 이미지 정보를 수정한다")
    @Test
    void updateGroupImageIsNotExist() {
        String expected = "https://image.moyeora.site/group/saved/imageName.png";
        BDDMockito.given(imageConnector.requestImageSave(Mockito.anyString(), Mockito.any()))
                .willReturn(expected);

        String actual = groupImageService.update(savedGroup.getHost().getId(), savedGroup.getId(), IMAGE);

        Optional<GroupImage> savedGroupImage = groupImageRepository.findByGroupId(savedGroup.getId());
        assertThat(savedGroupImage).isPresent();
        assertAll(
                () -> assertThat(actual).isEqualTo(expected),
                () -> assertThat(savedGroupImage.get().getImageName()).isEqualTo("imageName.png")
        );
    }

    @DisplayName("모임 이미지를 수정할 때 주최자가 아니면 예외가 발생한다")
    @Test
    void updateMemberIsNotHost() {
        Member member = memberRepository.save(new Member(UserId.momo("member"), password, UserName.from("momo")));

        assertThatThrownBy(() -> groupImageService.update(member.getId(), savedGroup.getId(), IMAGE))
                .isInstanceOf(MomoException.class)
                .hasMessage("모임의 주최자가 아닙니다.");
    }

    @DisplayName("모임 이미지를 수정할 때 모임이 진행중이 아니면 예외가 발생한다")
    @Test
    void updateGroupIsNotProceeding() {
        savedGroup.closeEarly();

        assertThatThrownBy(() -> groupImageService.update(savedGroup.getId(), savedGroup.getId(), IMAGE))
                .isInstanceOf(MomoException.class)
                .hasMessage("해당 모임은 조기 마감되어 있습니다.");
    }

    @DisplayName("모임의 이미지를 기본 이미지로 초기화한다")
    @Test
    void init() {
        GroupImage groupImage = new GroupImage(savedHost.getId(), "imageName.jpg");
        groupImageRepository.save(groupImage);

        groupImageService.init(savedHost.getId(), savedGroup.getId());

        Optional<GroupImage> savedGroupImage = groupImageRepository.findByGroupId(savedGroup.getId());
        String expected = savedGroup.getCategory().getDefaultImageName();
        assertThat(savedGroupImage).isPresent();
        assertAll(
                () -> assertThat(savedGroupImage.get().getGroupId()).isEqualTo(savedGroup.getId()),
                () -> assertThat(savedGroupImage.get().getImageName()).isEqualTo(expected)
        );
    }

    @DisplayName("이전에 저장된 이미지가 존재하지 않을 때 모임 이미지 정보를 초기화하면 기본 이미지 정보가 저장된다")
    @Test
    void initGroupImageIsNotExist() {
        groupImageService.init(savedGroup.getHost().getId(), savedGroup.getId());

        Optional<GroupImage> savedGroupImage = groupImageRepository.findByGroupId(savedGroup.getId());
        String expected = savedGroup.getCategory().getDefaultImageName();
        assertThat(savedGroupImage).isPresent();
        assertAll(
                () -> assertThat(savedGroupImage.get().getGroupId()).isEqualTo(savedGroup.getId()),
                () -> assertThat(savedGroupImage.get().getImageName()).isEqualTo(expected)
        );
    }

    @DisplayName("기본 이미지가 저장되어 있을 때 모임 이미지를 초기화한다")
    @Test
    void initGroupImageIsDefaultImage() {
        GroupImage groupImage = new GroupImage(savedHost.getId(), savedGroup.getCategory().getDefaultImageName());
        groupImageRepository.save(groupImage);

        groupImageService.init(savedGroup.getHost().getId(), savedGroup.getId());

        Optional<GroupImage> savedGroupImage = groupImageRepository.findByGroupId(savedGroup.getId());
        String expected = savedGroup.getCategory().getDefaultImageName();
        assertThat(savedGroupImage).isPresent();
        assertAll(
                () -> assertThat(savedGroupImage.get().getGroupId()).isEqualTo(savedGroup.getId()),
                () -> assertThat(savedGroupImage.get().getImageName()).isEqualTo(expected)
        );
    }

    @DisplayName("모임 이미지를 초기화할 때 주최자가 아니면 예외가 발생한다")
    @Test
    void initMemberIsNotHost() {
        Member member = memberRepository.save(new Member(UserId.momo("member"), password, UserName.from("momo")));

        assertThatThrownBy(() -> groupImageService.init(member.getId(), savedGroup.getId()))
                .isInstanceOf(MomoException.class)
                .hasMessage("모임의 주최자가 아닙니다.");
    }

    @DisplayName("모임 이미지를 초기화할 때 모임이 진행중이 아니면 예외가 발생한다")
    @Test
    void initGroupIsNotProceeding() {
        savedGroup.closeEarly();

        assertThatThrownBy(() -> groupImageService.init(savedGroup.getId(), savedGroup.getId()))
                .isInstanceOf(MomoException.class)
                .hasMessage("해당 모임은 조기 마감되어 있습니다.");
    }

    private Group saveGroup() {
        Group group = MOMO_STUDY.builder()
                .capacity(3)
                .toGroup(savedHost);
        return groupRepository.save(group);
    }
}
