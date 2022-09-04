package com.woowacourse.momo.group.service.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.group.domain.Group;
import com.woowacourse.momo.group.domain.participant.Participant;
import com.woowacourse.momo.member.domain.Member;

@Component
public class GroupSpecification {

    public Specification<Group> initialize() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
    }

    public Specification<Group> filterByHosted(Member member) {
        return (root, query, criteriaBuilder) -> isHost(member, root, criteriaBuilder);
    }

    private Predicate isHost(Member member, Root<Group> root, CriteriaBuilder builder) {
        return builder.equal(GroupAttribute.HOST.from(root), member);
    }

    public Specification<Group> filterByParticipated(Member member) {
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);

            Predicate isHost = isHost(member, root, criteriaBuilder);
            Predicate isParticipant = isParticipant(member, root, query, criteriaBuilder);
            return criteriaBuilder.or(isHost, isParticipant);
        };
    }

    private Predicate isParticipant(Member member, Root<Group> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        Subquery<Participant> subQuery = query.subquery(Participant.class);
        Root<Participant> subRoot = subQuery.from(Participant.class);

        subQuery.select(ParticipantAttribute.MEMBER_ID.from(subRoot))
                .where(builder.and(
                        builder.equal(ParticipantAttribute.GROUP_ID.from(subRoot), GroupAttribute.ID.from(root)),
                        builder.equal(ParticipantAttribute.MEMBER_ID.from(subRoot), member.getId()))
                );
        return builder.exists(subQuery);
    }

    public Specification<Group> filterByCategory(Long categoryId) {
        if (categoryId == null) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
        }
        Category category = Category.from(categoryId);
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(GroupAttribute.CATEGORY.from(root), category);
    }

    public Specification<Group> containKeyword(String keyword) {
        if (keyword == null) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
        }
        return (root, query, criteriaBuilder) -> {
            Predicate nameContainKeyword = criteriaBuilder.like(
                    GroupAttribute.NAME.from(root), "%" + keyword + "%");
            Predicate descriptionContainKeyword = criteriaBuilder.like(
                    GroupAttribute.DESCRIPTION.from(root), "%" + keyword + "%");
            return criteriaBuilder.or(nameContainKeyword, descriptionContainKeyword);
        };
    }

    public Specification<Group> excludeFinished(Boolean excludeFinished) {
        if (excludeFinished == null) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
        }
        return (root, query, criteriaBuilder) -> {
            query.groupBy(root);

            Predicate isNotParticipantSizeFull = isNotParticipantSizeFull(root, query, criteriaBuilder);
            query.having(isNotParticipantSizeFull);

            Predicate isEarlyClosed = criteriaBuilder.isFalse(GroupAttribute.CLOSED_EARLY.from(root));
            Predicate isOverDeadline = criteriaBuilder.greaterThan(GroupAttribute.DEADLINE.from(root),
                    criteriaBuilder.currentTimestamp());

            return criteriaBuilder.and(isEarlyClosed, isOverDeadline);
        };
    }

    private Predicate isNotParticipantSizeFull(Root<Group> root, CriteriaQuery<?> query,
                                               CriteriaBuilder criteriaBuilder) {
        Subquery<Long> subQuery = query.subquery(Long.class);
        Root<Member> subRoot = subQuery.from(Member.class);

        Expression<Long> memberId = MemberAttribute.ID.from(subRoot);

        subQuery.select(criteriaBuilder.count(memberId))
                .where(criteriaBuilder.or(
                        isHost(root, subRoot, criteriaBuilder),
                        isParticipant(root, memberId, subQuery, criteriaBuilder))
                );
        return criteriaBuilder.lessThan(subQuery, GroupAttribute.CAPACITY.from(root));
    }

    private Predicate isHost(Root<Group> root, Root<Member> member, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(GroupAttribute.HOST.from(root), member);
    }

    private Predicate isParticipant(Root<Group> root, Expression<Long> memberId, Subquery<Long> query,
                                    CriteriaBuilder builder) {
        Subquery<Long> subQuery = query.subquery(Long.class);
        Root<Participant> subRoot = subQuery.from(Participant.class);
        subQuery.select(ParticipantAttribute.MEMBER_ID.from(subRoot))
                .where(builder.equal(ParticipantAttribute.GROUP_ID.from(subRoot), GroupAttribute.ID.from(root)));
        return builder.in(memberId).value(subQuery);
    }

    public Specification<Group> orderByDeadline(Boolean orderByDeadline) {
        if (orderByDeadline == null) {
            return orderByIdDesc();
        }
        return (root, query, criteriaBuilder) -> {
            query.orderBy(criteriaBuilder.asc(GroupAttribute.DEADLINE.from(root)),
                    criteriaBuilder.desc(GroupAttribute.ID.from(root)));
            return criteriaBuilder.greaterThan(GroupAttribute.DEADLINE.from(root),
                    criteriaBuilder.currentTimestamp());
        };
    }

    private Specification<Group> orderByIdDesc() {
        return (root, query, criteriaBuilder) -> {
            query.orderBy(criteriaBuilder.desc(GroupAttribute.ID.from(root)));
            return criteriaBuilder.conjunction();
        };
    }
}
