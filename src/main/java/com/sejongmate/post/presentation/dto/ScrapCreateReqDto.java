package com.sejongmate.post.presentation.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScrapCreateReqDto {
    private Long userId;
    private Long postId;

    @Builder
    public ScrapCreateReqDto(Long userId, Long postId) {
        this.userId = userId;
        this.postId = postId;
    }
}
