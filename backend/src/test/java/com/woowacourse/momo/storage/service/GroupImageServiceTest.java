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

    @DisplayName("기본 이미지 정보를 저장한다")
    @Test
    void init() {
        groupImageService.init(savedGroup);

        Optional<GroupImage> groupImage = groupImageRepository.findByGroup(savedGroup);

        String imageName = savedGroup.getCategory().getDefaultImageName();
        assertThat(groupImage).isPresent();
        assertAll(
                () -> assertThat(groupImage.get().getGroup()).isEqualTo(savedGroup),
                () -> assertThat(groupImage.get().getImageName()).isEqualTo(imageName)
        );
    }

    @DisplayName("이미지 정보를 저장한다")
    @Test
    void save() {
        String expected = "https://image.moyeora.site/group/saved/imageName.png";
        BDDMockito.given(imageConnector.requestImageSave(Mockito.anyString(), Mockito.any()))
                .willReturn(expected);

        String actual = groupImageService.save(savedGroup.getHost().getId(), savedGroup.getId(), IMAGE);

        Optional<GroupImage> groupImage = groupImageRepository.findByGroup(savedGroup);
        assertThat(groupImage).isPresent();
        assertAll(
                () -> assertThat(actual).isEqualTo(expected),
                () -> assertThat(groupImage.get().getImageName()).isEqualTo("imageName.png")
        );
    }

    @DisplayName("이미지를 저장할 때 주최자가 아니면 예외가 발생한다")
    @Test
    void saveMemberIsNotHost() {
        Member member = memberRepository.save(new Member(UserId.momo("member"), password, UserName.from("momo")));

        assertThatThrownBy(() -> groupImageService.save(member.getId(), savedGroup.getId(), IMAGE))
                .isInstanceOf(MomoException.class)
                .hasMessage("모임의 주최자가 아닙니다.");
    }

    @DisplayName("이미지 정보를 삭제한다")
    @Test
    void delete() {
        BDDMockito.given(imageConnector.requestImageSave(Mockito.anyString(), Mockito.any()))
                .willReturn("https://image.moyeora.site/group/saved/imageName.png");
        groupImageService.save(savedGroup.getHost().getId(), savedGroup.getId(), IMAGE);

        groupImageService.delete(savedGroup);

        Optional<GroupImage> groupImage = groupImageRepository.findByGroup(savedGroup);
        assertThat(groupImage).isEmpty();
    }

    private Group saveGroup() {
        Group group = MOMO_STUDY.builder()
                .capacity(3)
                .toGroup(savedHost);
        return groupRepository.save(group);
    }
}
