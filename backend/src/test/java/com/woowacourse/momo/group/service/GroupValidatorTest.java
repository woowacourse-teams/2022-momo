package com.woowacourse.momo.group.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static com.woowacourse.momo.group.exception.GroupErrorCode.NOT_EXIST;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.group.exception.GroupException;

@Transactional
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
@SpringBootTest
class GroupValidatorTest {

    private final GroupValidator groupValidator;

    @DisplayName("존재하지 않는 모임 id가 아니면 예외를 발생시킨다")
    @Test
    void validateExistGroup() {
        Long notExistId = 100L;
        assertThatThrownBy(() -> groupValidator.validateExistGroup(notExistId))
                .isInstanceOf(GroupException.class)
                .hasMessage(NOT_EXIST.getMessage());
    }
}
