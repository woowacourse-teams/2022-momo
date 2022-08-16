package com.woowacourse.momo.storage.domain.image;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.woowacourse.momo.group.domain.group.Group;

@Getter
@NoArgsConstructor
@Entity
public class GroupImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "group_id")
    @OneToOne
    private Group group;

    private String fileName;

    public GroupImage(Group group, String fileName) {
        this.group = group;
        this.fileName = fileName;
    }

    public void updateFileName(String fileName) {
        this.fileName = fileName;
    }
}
