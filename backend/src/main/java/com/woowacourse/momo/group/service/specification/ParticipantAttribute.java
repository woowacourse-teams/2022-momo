package com.woowacourse.momo.group.service.specification;

import java.util.List;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import com.woowacourse.momo.group.domain.group.Group;
import com.woowacourse.momo.participant.domain.Participant;

public enum ParticipantAttribute {

    ID("id"),
    MEMBER("member"),
    ;

    private final String startAttribute;
    private final List<String> attributes;

    ParticipantAttribute(String startAttribute, String... attributes) {
        this.startAttribute = startAttribute;
        this.attributes = List.of(attributes);
    }

    public <T> Expression<T> from(Root<Group> root) {
        Path<T> path = joinParticipants(root).get(startAttribute);
        for (String attribute : attributes) {
            path = path.get(attribute);
        }
        return path;
    }

    private Join<Participant, Group> joinParticipants(Root<Group> root) {
        return root.join("participants").join("participants");
    }
}
