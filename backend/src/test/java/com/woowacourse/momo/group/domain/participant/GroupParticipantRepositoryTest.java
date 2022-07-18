package com.woowacourse.momo.group.domain.participant;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class GroupParticipantRepositoryTest {

    @Autowired
    private GroupParticipantRepository groupParticipantRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void test() {

    }
}