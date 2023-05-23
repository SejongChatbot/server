package com.sejongmate.post.presentation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentCreateReqDto {
    @NotNull(message = "user id를 입력하세요.")
    private Long userId;
    @NotNull(message = "post id를 입력하세요.")
    private Long postId;
    @NotNull(message = "내용을 입력하세요.")
    private String content;

    @Builder
    public CommentCreateReqDto(Long userId, Long postId, String content) {
        this.userId = userId;
        this.postId = postId;
        this.content = content;
    }
}
