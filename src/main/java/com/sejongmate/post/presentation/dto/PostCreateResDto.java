package com.sejongmate.post.presentation.dto;

import com.sejongmate.chat.domain.ChatMessage;
import com.sejongmate.chat.presentation.dto.ChatMessageResDto;
import com.sejongmate.post.domain.Post;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostCreateResDto {
    private Long id;
    private LocalDateTime createdDate;

    public static PostCreateResDto from(Post post){
        return PostCreateResDto.builder()
                .id(post.getId())
                .createdDate(post.getCreatedDate())
                .build();
    }
}
