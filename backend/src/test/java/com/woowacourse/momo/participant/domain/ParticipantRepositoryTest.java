package com.woowacourse.momo.participant.domain;

import static org.assertj.core.api.Assertions.assertThat;

import static com.woowacourse.momo.fixture.DateTimeFixture.내일_23시_59분;
import static com.woowacourse.momo.fixture.DurationFixture.이틀후부터_일주일후까지;
import static com.woowacourse.momo.fixture.ScheduleFixture.이틀후_10시부터_12시까지;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.woowacourse.momo.auth.support.SHA256Encoder;
import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.group.domain.calendar.Schedule;
import com.woowacourse.momo.group.domain.group.Group;
import com.woowacourse.momo.group.domain.group.GroupRepository;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;
import com.woowacourse.momo.member.domain.Password;

@DataJpaTest
class ParticipantRepositoryTest {

	@Autowired
	private ParticipantRepository participantRepository;

	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private EntityManager entityManager;

	private static final Password PASSWORD = Password.encrypt("momo123!", new SHA256Encoder());
	private static final Member HOST = new Member("주최자", PASSWORD, "모모");
	private static final Member PARTICIPANT = new Member("참여자", PASSWORD, "모모");
	private static Group group;

	private Member savedHost;
	private Member savedParticipant;
	private Group savedGroup;

	@BeforeEach
	void setUp() {
		savedHost = memberRepository.save(HOST);
		savedParticipant = memberRepository.save(PARTICIPANT);

		List<Schedule> schedules = List.of(이틀후_10시부터_12시까지.newInstance());
		group = new Group("모임", savedHost, Category.CAFE, 3, 이틀후부터_일주일후까지.getInstance(),
			내일_23시_59분.getInstance(), schedules, "", "");
		savedGroup = groupRepository.save(group);
	}

	@DisplayName("모임에 탈퇴한다")
	@Test
	void deleteByGroupIdAndMemberId() {
		Participant participant = participantRepository.save(new Participant(savedGroup, savedParticipant));
		synchronize();

		participantRepository.deleteByGroupIdAndMemberId(savedGroup.getId(), savedParticipant.getId());
		synchronize();

		Optional<Participant> actual = participantRepository.findById(participant.getId());
		assertThat(actual).isEmpty();

	}

	private void synchronize() {
		entityManager.flush();
		entityManager.clear();
	}
}
