package com.sejongmate.post.presentation.dto;

import com.sejongmate.post.domain.Comment;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentCreateResDto {
    private Long id;
    private LocalDateTime createdDate;

    public static CommentCreateResDto from(Comment comment){
        return CommentCreateResDto.builder()
                .id(comment.getId())
                .createdDate(comment.getCreatedDate())
                .build();
    }
}
