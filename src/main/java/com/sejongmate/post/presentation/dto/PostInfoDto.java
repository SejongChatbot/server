package com.sejongmate.post.presentation.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class PostInfoDto {
    private Long postId;
    private String title;
    private String content;
    private Integer num;
    private LocalDateTime startAt;
    private LocalDateTime endAt;

    @Builder @QueryProjection
    public PostInfoDto(Long postId, String title, String content, Integer num, LocalDateTime startAt, LocalDateTime endAt) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.num = num;
        this.startAt = startAt;
        this.endAt = endAt;
    }
}
