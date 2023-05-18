package com.sejongmate.chat.application;

import com.sejongmate.chat.domain.*;
import com.sejongmate.chat.infra.ChatQueryDao;
import com.sejongmate.chat.presentation.dto.*;
import com.sejongmate.common.BaseException;
import com.sejongmate.user.domain.User;
import com.sejongmate.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.sejongmate.common.BaseResponseStatus.INVALID_CHATROOM;
import static com.sejongmate.common.BaseResponseStatus.INVALID_USER_NUM;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
@Log4j2
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final ChatQueryDao chatQueryDao;

    @Transactional
    public ChatMessageResDto sendMessage(ChatMessageReqDto chatMessageReqDto) throws BaseException {

        ChatRoom room = chatRoomRepository.findById(chatMessageReqDto.getRoomId())
                .orElseThrow(() -> {
                    log.error(INVALID_CHATROOM.getMessage());
                    return new BaseException(INVALID_CHATROOM);
                });

        User user = userRepository.findByNum(chatMessageReqDto.getSenderNum()).orElseThrow(() -> {
            log.error(INVALID_USER_NUM.getMessage());
            return  new BaseException(INVALID_USER_NUM);
        });

        ChatMessage message = ChatMessage.builder()
                .type(chatMessageReqDto.getType())
                .room(room)
                .sender(user)
                .content(chatMessageReqDto.getContent())
                .createdAt(LocalDateTime.now())
                .isNotice(Boolean.FALSE)
                .fileUrl(chatMessageReqDto.getFileUrl())
                .build();

        chatMessageRepository.save(message);

        return ChatMessageResDto.from(message);
    }

    @Transactional
    public ChatRoomResDto createChatRoom(ChatRoomReqDto chatRoomReqDto) {

        ChatRoom room = ChatRoom.createChatRoom(chatRoomReqDto.getName());

        List<ChatParticipant> participants = new ArrayList<>();
        for (String num : chatRoomReqDto.getParticipantNum()) {
            User user = userRepository.findByNum(num).orElseThrow(() -> {
                log.error(INVALID_USER_NUM.getMessage());
                return  new BaseException(INVALID_USER_NUM);
            });

            participants.add(ChatParticipant.builder()
                    .room(room)
                    .user(user)
                    .build());
        }

        room.insertParticipants(participants);

        chatRoomRepository.save(room);

        return ChatRoomResDto.from(room);
    }


    public List<ChatRoomInfoDto> getChatRoomList(Long id) {
        return chatQueryDao.getChatRoomInfo(id);
    }

    public List<ChatRoomHistoryDto> getChatRoomHistory(String id){
        return chatQueryDao.getChatRoomHistory(id);
    }

    @Transactional
    public Boolean deleteChatRoomUser(String roomId, Long userId) {
        return chatQueryDao.deleteChatRoomUser(roomId, userId);
    }
}
