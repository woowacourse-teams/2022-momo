package com.woowacourse.momo.storage.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.TestConstructor;

import lombok.RequiredArgsConstructor;

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest(includeFilters = @ComponentScan.Filter(classes = Repository.class))
class GroupImageRepositoryTest {

    private final GroupImageRepository groupImageRepository;

    private static final long GROUP_ID = 1L;
    private static final String IMAGE_NAME = "imageName.png";
    private static final GroupImage GROUP_IMAGE = new GroupImage(GROUP_ID, IMAGE_NAME);

    @DisplayName("그룹 아이디를 이용해 그룹 이미지 정보를 조회한다")
    @Test
    void findByGroupId() {
        groupImageRepository.save(GROUP_IMAGE);

        Optional<GroupImage> groupImage = groupImageRepository.findByGroupId(GROUP_ID);

        assertThat(groupImage).isPresent();
        assertThat(groupImage.get()).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(GROUP_IMAGE);
    }

    @DisplayName("그룹 아이디를 이용해 그룹 이미지 정보를 삭제한다")
    @Test
    void deleteByGroupId() {
        groupImageRepository.save(GROUP_IMAGE);

        groupImageRepository.deleteByGroupId(GROUP_ID);

        Optional<GroupImage> groupImage = groupImageRepository.findByGroupId(GROUP_ID);
        assertThat(groupImage).isEmpty();
    }
}
