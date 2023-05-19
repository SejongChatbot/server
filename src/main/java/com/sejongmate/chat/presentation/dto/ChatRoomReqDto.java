package com.sejongmate.chat.presentation.dto;

import com.sejongmate.chat.domain.ChatParticipant;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoomReqDto {
    @NotNull(message = "채팅방 이름을 입력하세요.")
    private String name;
    private List<String> participantNum;

    @Builder
    public ChatRoomReqDto(String name, List<String> participantNum) {
        this.name = name;
        this.participantNum = participantNum;
    }
}
