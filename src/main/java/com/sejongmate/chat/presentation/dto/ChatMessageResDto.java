package com.sejongmate.chat.presentation.dto;

import com.sejongmate.chat.domain.ChatMessage;
import com.sejongmate.chat.domain.ChatMessageRepository;
import com.sejongmate.user.domain.User;
import com.sejongmate.user.presentation.dto.UserCreateResDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatMessageResDto {
    Long id;
    LocalDateTime createdAt;

    public static ChatMessageResDto from(ChatMessage chatMessage){
        return ChatMessageResDto.builder()
                .id(chatMessage.getId())
                .createdAt(chatMessage.getCreatedAt())
                .build();
    }
}
