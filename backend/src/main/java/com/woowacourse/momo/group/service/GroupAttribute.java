package com.woowacourse.momo.group.service;

import java.util.List;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import com.woowacourse.momo.group.domain.group.Group;

public enum GroupAttribute {

    ID("id"),
    HOST("host"),
    NAME("name", "value"),
    CATEGORY("category"),
    CAPACITY("participants", "capacity"),
    DEADLINE("calendar", "deadline"),
    DESCRIPTION("description"),
    IS_EARLY_CLOSED("isEarlyClosed");

    private final String startAttribute;
    private final List<String> attributes;

    GroupAttribute(String startAttribute, String... attributes) {
        this.startAttribute = startAttribute;
        this.attributes = List.of(attributes);
    }

    public <T> Expression<T> from(Root<Group> root) {
        Path<T> path = root.get(startAttribute);
        for (String attribute : attributes) {
            path = path.get(attribute);
        }
        return path;
    }
}
