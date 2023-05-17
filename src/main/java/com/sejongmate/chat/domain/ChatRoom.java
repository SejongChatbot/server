package com.sejongmate.chat.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom {
    @Id
    @Column(name = "room_id")
    private String id;
    private String name;
    private LocalDateTime createdAt;

    private Integer count;

//    @JsonIgnore
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<ChatParticipant> participants;

    @Builder
    public ChatRoom(String id, String name, LocalDateTime createdAt, Integer count, List<ChatParticipant> participants) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.count = count;
        this.participants = participants;
    }

    //==생성 메서드==//
    public static ChatRoom createChatRoom(String name){
        ChatRoom chatRoom = new ChatRoom();
        String randomId = UUID.randomUUID().toString();

        chatRoom.id = randomId;
        chatRoom.name = name;
        chatRoom.createdAt = LocalDateTime.now();
        chatRoom.count = 0;
        chatRoom.participants = new ArrayList<>();

        return chatRoom;
    }

    //==비지니스 로직==//
    public void insertParticipants(List<ChatParticipant> chatParticipants){
        for (ChatParticipant participant : chatParticipants) {
            this.participants.add(participant);
            this.count += 1;
        }
    }
}

