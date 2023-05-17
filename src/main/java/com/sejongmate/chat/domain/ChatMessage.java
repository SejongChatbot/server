package com.sejongmate.chat.domain;

import com.sejongmate.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessage {
    @Id
    @Column(name = "message_id")
    private Long id;
    @Enumerated(EnumType.STRING)
    private MessageType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private ChatRoom room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private User sender;
    private String content;
    private LocalDateTime createdAt;
    private Boolean isNotice;
    private String fileUrl;

    @Builder
    public ChatMessage(MessageType type, ChatRoom room, User sender, String content, LocalDateTime createdAt, Boolean isNotice, String fileUrl) {
        this.type = type;
        this.room = room;
        this.sender = sender;
        this.content = content;
        this.createdAt = createdAt;
        this.isNotice = isNotice;
        this.fileUrl = fileUrl;
    }
}
