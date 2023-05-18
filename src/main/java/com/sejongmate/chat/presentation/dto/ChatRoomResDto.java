package com.sejongmate.chat.presentation.dto;
import com.sejongmate.chat.domain.ChatRoom;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatRoomResDto {
    String id;
    LocalDateTime createdAt;

    public static ChatRoomResDto from(ChatRoom chatRoom){
        return ChatRoomResDto.builder()
                .id(chatRoom.getId())
                .createdAt(chatRoom.getCreatedAt())
                .build();
    }
}
