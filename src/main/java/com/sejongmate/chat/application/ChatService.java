package com.sejongmate.chat.application;

import com.sejongmate.chat.domain.ChatMessage;
import com.sejongmate.chat.domain.ChatMessageRepository;
import com.sejongmate.chat.domain.ChatRoom;
import com.sejongmate.chat.domain.ChatRoomRepository;
import com.sejongmate.chat.presentation.dto.ChatMessageReqDto;
import com.sejongmate.chat.presentation.dto.ChatMessageResDto;
import com.sejongmate.common.BaseException;
import com.sejongmate.user.domain.User;
import com.sejongmate.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
    @Transactional
    public ChatMessageResDto sendMessage(ChatMessageReqDto chatMessageReqDto) throws BaseException {

        ChatRoom room = chatRoomRepository.findById(chatMessageReqDto.getRoomId())
                .orElseThrow(() -> {
                    log.error(INVALID_CHATROOM.getMessage());
                    return new BaseException(INVALID_CHATROOM);
                });

        User user = userRepository.findById(chatMessageReqDto.getSenderId()).orElseThrow(() -> {
            log.error(INVALID_USER_NUM.getMessage());
            return  new BaseException(INVALID_USER_NUM);
        });


        ChatMessage message = ChatMessage.builder()
                .type(chatMessageReqDto.getType())
                .room(room)
                .sender(user)
                .content(chatMessageReqDto.getContent())
                .createdAt(LocalDateTime.now())
                .isNotice(chatMessageReqDto.getIsNotice())
                .fileUrl(chatMessageReqDto.getFileUrl())
                .build();

        chatMessageRepository.save(message);

        return ChatMessageResDto.from(message);

    }
}
