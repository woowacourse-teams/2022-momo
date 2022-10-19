package com.woowacourse.momo.group.service.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.woowacourse.momo.member.service.dto.response.MemberResponse;

@Getter
@AllArgsConstructor
public class GroupSummaryResponse {

    private Long id;
    private String name;
    private MemberResponse host;
    private Long categoryId;
    private int capacity;
    private int numOfParticipant;
    private boolean finished;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime deadline;
    private boolean like;
    private String imageUrl;
}
