package com.sejongmate.chat.presentation.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
public class ChatRoomInfoDto {
    String roomId;
    String name;
    LocalDateTime lastMessageAt;
    String lastMessage;
    Integer num;

    @Builder @QueryProjection
    public ChatRoomInfoDto(String roomId, String name, LocalDateTime lastMessageAt, String lastMessage, Integer num) {
        this.roomId = roomId;
        this.name = name;
        this.lastMessageAt = lastMessageAt;
        this.lastMessage = lastMessage;
        this.num = num;
    }
}
