package com.woowacourse.momo.util;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.category.domain.Category;

@Component
@RequiredArgsConstructor
public class DataGenerator {

    private static final Logger log = LoggerFactory.getLogger(DataGenerator.class);

    private static final int TOTAL_MEMBER_SIZE = 1_000_000;
    private static final int TOTAL_GROUP_SIZE = 1_000_000;
    private static final int BATCH_SIZE = 1000; // 한번에 저장할 데이터 크기
    private static final int THREAD_COUNT = 5; // 운용할 스레드 개수
    private static final String MEMBER_INSERT_SQL = "insert into momo_member(id, deleted, user_id, name, password) values (?, ?, ?, ?, ?)";
    private static final String GROUP_INSERT_SQL =
            "insert into momo_group(id, capacity, category, deadline, description, endDate, startDate, closedEarly, "
                    + "locationAddress, locationBuildingName, locationDetail, name, host_id) "
                    + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static long participantId = 1;

    ExecutorService executorService;

    private final JdbcTemplate jdbcTemplate;


    public void dbInit() throws InterruptedException {
        executeMethodAndAwaitTerminate(this::saveMembers);
        executeMethodAndAwaitTerminate(this::saveGroups);
        executeMethodAndAwaitTerminate(this::saveParticipants);
    }

    private void executeMethodAndAwaitTerminate(Runnable runnable) throws InterruptedException {
        executorService = Executors.newFixedThreadPool(
                THREAD_COUNT
        );

        runnable.run();
        executorService.shutdown();
        while (!executorService.awaitTermination(100, TimeUnit.MILLISECONDS));
    }

    private void saveMembers() {
        final int memberBlockSize = TOTAL_MEMBER_SIZE / THREAD_COUNT; // 한 쓰레드가 맡아서 저장해야하는 유저 수

        for (int threadNumber = 0; threadNumber < THREAD_COUNT; threadNumber++) {
            saveMemberByThread(memberBlockSize, threadNumber);
        }
    }

    private void saveMemberByThread(int memberBlockSize, int threadNumber) {
        executorService.execute(() -> {
            log.info("Member 저장 시작-" + threadNumber);
            for (int i = 0; i < memberBlockSize / BATCH_SIZE; i++) {
                saveMemberBlock(memberBlockSize, threadNumber, i);
            }
            log.info("Member 저장 종료-" + threadNumber);
        });
    }

    private void saveMemberBlock(int memberBlockSize, int threadNumber, int i) {
        int currentMemberBlockStartId = memberBlockSize * threadNumber;
        List<MemberDto> generatedMember = makeMember(
                currentMemberBlockStartId + BATCH_SIZE * i + 1,
                currentMemberBlockStartId + BATCH_SIZE * (i + 1)
        );

        jdbcTemplate.batchUpdate(MEMBER_INSERT_SQL, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                MemberDto member = generatedMember.get(i);
                ps.setLong(1, member.getId());
                ps.setBoolean(2, false);
                ps.setString(3, member.getUserId());
                ps.setString(4, member.getUserName());
                ps.setString(5, member.getPassword());
            }

            @Override
            public int getBatchSize() {
                return generatedMember.size();
            }
        });
    }

    private List<MemberDto> makeMember(int startId, int endId) {
        List<MemberDto> members = new ArrayList<>();
        for (long i = startId; i <= endId; i++) {
            members.add(new MemberDto(i));
        }
        return members;
    }

    private void saveGroups() {
        // 5000명의 호스트에 대해, 인당 100개의 모임 주최

        for (int threadNumber = 0; threadNumber < THREAD_COUNT; threadNumber++) {
            final int groupBlockSize = TOTAL_GROUP_SIZE / THREAD_COUNT;
            saveGroupByThread(threadNumber, groupBlockSize);
        }
    }

    private void saveGroupByThread(int threadNumber, int groupBlockSize) {
        executorService.execute(() -> {
            log.info("Group 저장 시작-" + threadNumber);
            for (int i = 0; i < groupBlockSize / BATCH_SIZE; i++) {
                saveGroupBlock(threadNumber, groupBlockSize, i);
            }
            log.info("Group 저장 종료-" + threadNumber);
        });
    }

    private void saveGroupBlock(int threadNumber, int groupBlockSize, int i) {
        int currentGroupBlockStartId = groupBlockSize * threadNumber;
        List<GroupDto> groupDtos = makeGroup(
                currentGroupBlockStartId + BATCH_SIZE * i + 1,
                currentGroupBlockStartId + BATCH_SIZE * (i + 1)
        );

        String sql = GROUP_INSERT_SQL;
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                GroupDto groupDto = groupDtos.get(i);
                ps.setLong(1, groupDto.getId());
                ps.setInt(2, groupDto.getCapacity());
                ps.setString(3, groupDto.getCategory());
                ps.setDate(4, Date.valueOf(groupDto.getDeadline().toLocalDate()));
                ps.setString(5, groupDto.getDescription());
                ps.setDate(6, Date.valueOf(groupDto.getEndDate()));
                ps.setDate(7, Date.valueOf(groupDto.getStartDate()));
                ps.setBoolean(8, false);
                ps.setString(9, "");
                ps.setString(10, "");
                ps.setString(11, "");
                ps.setString(12, groupDto.getName());
                ps.setLong(13, groupDto.getHostId());
            }

            @Override
            public int getBatchSize() {
                return groupDtos.size();
            }
        });
    }

    private List<GroupDto> makeGroup(int src, int dest) {
        List<GroupDto> groupDtos = new ArrayList<>();

        for (int i = src; i <= dest; i++) {
            long addTime = ThreadLocalRandom.current().nextLong(10);
            long minusTime = ThreadLocalRandom.current().nextLong(10);
            LocalDate startDate = LocalDate.now().plusDays(addTime).minusDays(minusTime);
            Category generatedCategory = Category.from(ThreadLocalRandom.current().nextLong(10) + 1);
            GroupDto groupDto = new GroupDto(
                    i,
                    5,
                    generatedCategory.name(),
                    LocalDateTime.of(startDate.minusDays(1), LocalTime.now()),
                    "설명",
                    startDate.plusDays(addTime),
                    startDate,
                    ThreadLocalRandom.current().nextBoolean(),
                    "",
                    "그룹" + i,
                    ThreadLocalRandom.current().nextInt(1000) + 1
            );
            groupDtos.add(groupDto);
        }

        return groupDtos;
    }

    private void saveParticipants() {
        for (int threadNumber = 0; threadNumber < THREAD_COUNT; threadNumber++) {
            final int threadBlockSize = TOTAL_GROUP_SIZE / THREAD_COUNT;
            saveParticipantByThread(threadNumber, threadBlockSize);
        }
    }

    private void saveParticipantByThread(int threadNumber, int threadBlockSize) {
        executorService.execute(() -> {
            log.info("Participant 저장 시작-" + threadNumber);
            for (int i = 0; i < threadBlockSize / BATCH_SIZE; i++) {
                saveParticipantBlock(threadNumber, threadBlockSize, i);
            }
            log.info("Participant 저장 완료-" + threadNumber);
        });
    }

    private void saveParticipantBlock(int threadNumber, int threadBlockSize, int i) {
        String sql = "insert into momo_participant(id, member_id, group_id) values (?, ?, ?)";
        List<ParticipantDto> generatedParticipant = makeParticipant(
                (threadBlockSize * threadNumber) + BATCH_SIZE * i + 1,
                (threadBlockSize * threadNumber) + BATCH_SIZE * (i + 1)
        );

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ParticipantDto participant = generatedParticipant.get(i);
                ps.setLong(1, participant.getId());
                ps.setLong(2, participant.getMember_id());
                ps.setLong(3, participant.getGroup_id());
            }

            @Override
            public int getBatchSize() {
                return generatedParticipant.size();
            }
        });
    }

    private List<ParticipantDto> makeParticipant(int src, int dest) {
        List<ParticipantDto> participants = new ArrayList<>();
        for (long groupId = src; groupId <= dest; groupId++) {

            // 1001번~1010번의 사람이 고르게 그룹에 가입
            int participantCount = ThreadLocalRandom.current().nextInt(10);
            for (long memberId = 1001; memberId < 1001 + participantCount; memberId++) {
                long id = 0;
                synchronized(this) {
                    id = participantId++;
                }
                participants.add(new ParticipantDto(id, memberId, groupId));
            }
        }
        return participants;
    }

    @Getter
    private class MemberDto {
        private long id;
        private String userId;
        private String password;
        private String userName;

        public MemberDto(long id) {
            this.id = id;
            this.userId = "user" + id;
            this.password = "qwe123!" + id;
            this.userName = "name" + id;
        }

    }
    @Getter
    @AllArgsConstructor
    private class GroupDto {

        long id;
        int capacity;
        String category;
        LocalDateTime deadline;
        String description;
        LocalDate endDate;
        LocalDate startDate;
        boolean closedEarly;
        String location;
        String name;
        long hostId;

    }
    @Getter
    private class ParticipantDto {

        private long id;
        private long member_id;
        private long group_id;

        public ParticipantDto(long id, long member_id, long group_id) {
            this.id = id;
            this.member_id = member_id;
            this.group_id = group_id;
        }
    }
}