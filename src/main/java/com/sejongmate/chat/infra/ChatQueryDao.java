package com.sejongmate.chat.infra;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sejongmate.chat.presentation.dto.ChatRoomInfoDto;
import com.sejongmate.chat.presentation.dto.QChatRoomInfoDto;
import com.sejongmate.common.util.DeduplicationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import static com.sejongmate.chat.domain.QChatMessage.chatMessage;
import static com.sejongmate.chat.domain.QChatParticipant.chatParticipant;
import static com.sejongmate.chat.domain.QChatRoom.chatRoom;

@RequiredArgsConstructor
@Repository
public class ChatQueryDao {
    private final JPAQueryFactory query;

    public List<ChatRoomInfoDto> getChatRoomInfo(Long id){


        List<ChatRoomInfoDto> chatRoomInfoDtos = query
                .select(new QChatRoomInfoDto(chatRoom.id, chatRoom.name,
                        chatMessage.createdAt, chatMessage.content, chatRoom.count
                ))
                .from(chatRoom)
                .where(chatParticipant.user.id.eq(id))
                .leftJoin(chatMessage).on(chatMessage.room.id.eq(chatRoom.id))
                .leftJoin(chatParticipant).on(chatParticipant.room.id.eq(chatRoom.id))
                .orderBy(chatMessage.createdAt.desc())
                .fetch();

        return DeduplicationUtils.deduplication(chatRoomInfoDtos, ChatRoomInfoDto::getRoomId);
    }

}
