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

import com.woowacourse.momo.member.domain.Member;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MemberImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "member_id")
    @OneToOne
    private Member member;

    private String fileName;

    public MemberImage(Member member, String fileName) {
        this.member = member;
        this.fileName = fileName;
    }

    public void updateFileName(String fileName) {
        this.fileName = fileName;
    }
}
