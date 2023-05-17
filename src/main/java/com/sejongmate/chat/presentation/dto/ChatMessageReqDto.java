package com.sejongmate.chat.presentation.dto;

import com.sejongmate.chat.domain.MessageType;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessageReqDto {
    @NotNull(message = "메세지 타입을 입력하세요.")
    private MessageType type;
    @NotNull(message = "room id를 입력하세요.")
    private String roomId;
    @NotNull(message = "sender id를 입력하세요.")
    private Long senderId;
    @NotNull(message = "내용을 입력하세요.")
    private String content;
    private LocalDateTime createdAt;
    private Boolean isNotice;
    private String fileUrl;

    @Builder
    public ChatMessageReqDto(MessageType type, String roomId, Long senderId, String content, LocalDateTime createdAt, Boolean isNotice, String fileUrl) {
        this.type = type;
        this.roomId = roomId;
        this.senderId = senderId;
        this.content = content;
        this.createdAt = createdAt;
        this.isNotice = isNotice;
        this.fileUrl = fileUrl;
    }
}
