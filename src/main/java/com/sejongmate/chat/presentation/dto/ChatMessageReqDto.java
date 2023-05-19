package com.sejongmate.chat.presentation.dto;

import com.sejongmate.chat.domain.MessageType;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessageReqDto {
    @NotNull(message = "메세지 타입을 입력하세요.")
    private MessageType type;
    @NotNull(message = "room id를 입력하세요.")
    private String roomId;
    @NotNull(message = "sender num를 입력하세요.")
    private String senderNum;
    @NotNull(message = "내용을 입력하세요.")
    private String content;

    @Builder
    public ChatMessageReqDto(MessageType type, String roomId, String senderNum, String content) {
        this.type = type;
        this.roomId = roomId;
        this.senderNum = senderNum;
        this.content = content;
    }
}
