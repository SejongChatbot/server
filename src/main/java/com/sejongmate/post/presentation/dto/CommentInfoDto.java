package com.sejongmate.post.presentation.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentInfoDto {
    String num;
    String content;
    LocalDateTime createdDate;

    @Builder @QueryProjection
    public CommentInfoDto(String num, String content, LocalDateTime createdDate) {
        this.num = num;
        this.content = content;
        this.createdDate = createdDate;
    }
}
