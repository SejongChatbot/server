package com.sejongmate.chat.presentation.dto;


import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class ChatRoomHistoryDto {
    private String name;
    private String profileUrl;
    private String content;
    private LocalDateTime createdAt;

    @Builder @QueryProjection
    public ChatRoomHistoryDto(String name, String profileUrl, String content, LocalDateTime createdAt) {
        this.name = name;
        this.profileUrl = profileUrl;
        this.content = content;
        this.createdAt = createdAt;
    }
}
