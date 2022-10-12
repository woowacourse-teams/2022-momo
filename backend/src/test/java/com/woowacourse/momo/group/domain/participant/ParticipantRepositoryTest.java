package com.woowacourse.momo.group.domain.participant;

import static org.assertj.core.api.Assertions.assertThat;

import static com.woowacourse.momo.fixture.GroupFixture.DUDU_STUDY;
import static com.woowacourse.momo.fixture.GroupFixture.MOMO_STUDY;
import static com.woowacourse.momo.fixture.MemberFixture.DUDU;
import static com.woowacourse.momo.fixture.MemberFixture.GUGU;
import static com.woowacourse.momo.fixture.MemberFixture.MOMO;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.TestConstructor;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.group.domain.Group;
import com.woowacourse.momo.group.domain.GroupRepository;
import com.woowacourse.momo.group.domain.search.GroupSearchRepository;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest(includeFilters = @ComponentScan.Filter(classes = Repository.class))
class ParticipantRepositoryTest {

    private final GroupRepository groupRepository;
    private final GroupSearchRepository groupSearchRepository;
    private final MemberRepository memberRepository;
    private final ParticipantRepository participantRepository;
    private final EntityManager entityManager;

    Member member1;
    Member member2;
    Member member3;
    Group group1;
    Group group2;

    @BeforeEach
    void setUp() {
        member1 = memberRepository.save(MOMO.toMember());
        member2 = memberRepository.save(DUDU.toMember());
        member3 = memberRepository.save(GUGU.toMember());

        group1 = groupRepository.save(MOMO_STUDY.toGroup(member1));
        group2 = groupRepository.save(DUDU_STUDY.toGroup(member1));
        group1.participate(member2);
        group1.participate(member3);
        group2.participate(member3);
    }

    @DisplayName("모임 ID를 통해 연관된 참여자를 모두 삭제한다")
    @Test
    void deleteAllByGroupId() {
        participantRepository.deleteAllByGroupId(group1.getId());
        synchronize();

        Optional<Group> foundGroup = groupSearchRepository.findById(group1.getId());
        assertThat(foundGroup).isPresent();

        List<Member> expected = foundGroup.get().getParticipants();

        int onlyHost = 1;
        assertThat(expected).hasSize(onlyHost);
    }

    @DisplayName("회원 ID와 모임 리스트에 연관된 참여자를 모두 삭제한다")
    @Test
    void deleteAllByMemberIdInGroups() {
        participantRepository.deleteAllByMemberIdInGroups(member3.getId(), List.of(group1, group2));
        synchronize();

        Optional<Group> foundGroup = groupSearchRepository.findById(group2.getId());
        assertThat(foundGroup).isPresent();

        List<Member> expected = foundGroup.get().getParticipants();

        int onlyHost = 1;
        assertThat(expected).hasSize(onlyHost);
    }

    void synchronize() {
        entityManager.flush();
        entityManager.clear();
    }
}
