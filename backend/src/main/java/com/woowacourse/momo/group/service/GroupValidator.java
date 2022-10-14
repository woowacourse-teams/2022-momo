package com.woowacourse.momo.group.service;

import static com.woowacourse.momo.group.exception.GroupErrorCode.NOT_EXIST;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.group.domain.search.GroupSearchRepository;
import com.woowacourse.momo.group.exception.GroupException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class GroupValidator {

    private final GroupSearchRepository groupSearchRepository;

    public void validateExistGroup(Long groupId) {
        boolean isExist = groupSearchRepository.existsById(groupId);
        if (!isExist) {
            throw new GroupException(NOT_EXIST);
        }
    }
}
