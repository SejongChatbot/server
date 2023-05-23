package com.sejongmate.post.presentation.dto;

import com.sejongmate.post.domain.Category;
import com.sejongmate.post.domain.MeetingType;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCreateReqDto {
    @NotNull(message = "user id를 입력하세요.")
    private Long userId;
    @NotNull(message = "카테고리를 입력하세요.")
    private Category category;
    @NotNull(message = "대면 여부를 입력하세요.")
    private MeetingType type;
    @NotNull(message = "제목을 입력하세요.")
    private String title;
    @NotNull(message = "내용을 입력하세요.")
    private String content;
    @NotNull(message = "모집 시작일을 입력하세요.")
    private LocalDateTime startAt;
    @NotNull(message = "모집 마감일을 입력하세요.")
    private LocalDateTime endAt;
    @NotNull(message = "모집 인원을 입력하세요.")
    private Integer num;

    @Builder
    public PostCreateReqDto(Long userId, Category category, MeetingType type, String title, String content, LocalDateTime startAt, LocalDateTime endAt, Integer num) {
        this.userId = userId;
        this.category = category;
        this.type = type;
        this.title = title;
        this.content = content;
        this.startAt = startAt;
        this.endAt = endAt;
        this.num = num;
    }
}
