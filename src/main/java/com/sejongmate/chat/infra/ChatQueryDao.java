package com.sejongmate.chat.infra;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sejongmate.chat.presentation.dto.ChatRoomHistoryDto;
import com.sejongmate.chat.presentation.dto.ChatRoomInfoDto;
import com.sejongmate.chat.presentation.dto.QChatRoomHistoryDto;
import com.sejongmate.chat.presentation.dto.QChatRoomInfoDto;
import com.sejongmate.common.util.DeduplicationUtils;
import com.sejongmate.user.domain.QUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import static com.sejongmate.chat.domain.QChatMessage.chatMessage;
import static com.sejongmate.chat.domain.QChatParticipant.chatParticipant;
import static com.sejongmate.chat.domain.QChatRoom.chatRoom;
import static com.sejongmate.user.domain.QUser.user;

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

    public List<ChatRoomHistoryDto> getChatRoomHistory(String id) {
        return query
                .select(new QChatRoomHistoryDto(user.name, user.profileUrl, chatMessage.content, chatMessage.createdAt))
                .from(chatMessage)
                .where(chatMessage.room.id.eq(id))
                .leftJoin(user).on(chatMessage.sender.id.eq(user.id))
                .orderBy(chatMessage.createdAt.asc())
                .fetch();
    }

    public Boolean deleteChatRoomUser(String roomId, Long userId){
        long result = query.delete(chatParticipant)
                .where(chatParticipant.room.id.eq(roomId), chatParticipant.user.id.eq(userId))
                .execute();
        return result == 1? true : false;
    }
}
