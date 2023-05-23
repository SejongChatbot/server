package com.sejongmate.post.presentation.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.sejongmate.post.domain.MeetingType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostListDto {
    Long postId;
    MeetingType meetingType;
    String title;
    LocalDateTime endAt;

    @Builder @QueryProjection
    public PostListDto(Long postId, MeetingType meetingType, String title, LocalDateTime endAt) {
        this.postId = postId;
        this.meetingType = meetingType;
        this.title = title;
        this.endAt = endAt;
    }
}
