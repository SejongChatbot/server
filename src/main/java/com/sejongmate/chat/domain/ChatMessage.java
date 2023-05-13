package com.sejongmate.chat.domain;

import com.sejongmate.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessage {
    public enum MessageType{
        MESSAGE, IMAGE
    }

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

}
