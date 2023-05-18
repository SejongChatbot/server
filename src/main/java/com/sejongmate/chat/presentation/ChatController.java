package com.sejongmate.chat.presentation;

import com.sejongmate.chat.application.ChatService;
import com.sejongmate.chat.presentation.dto.*;
import com.sejongmate.common.BaseException;
import com.sejongmate.common.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/chat")
@Log4j2
public class ChatController {
    private final ChatService chatService;

    /**
     * 채팅 생성
     */
    @PostMapping("/message")
    public BaseResponse<ChatMessageResDto> sendMessage(@Valid @RequestBody ChatMessageReqDto chatMessageReqDto, BindingResult br) {

        if (br.hasErrors()){
            String errorName = br.getAllErrors().get(0).getDefaultMessage();
            log.error(errorName);
            return new BaseResponse<>(errorName);
        }

        try {
            return new BaseResponse<>(chatService.sendMessage(chatMessageReqDto));
        } catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 채팅방 생성
     */
    @PostMapping("/room")
    public BaseResponse<ChatRoomResDto> createChatRoom(@Valid @RequestBody ChatRoomReqDto chatRoomReqDto, BindingResult br){
        if (br.hasErrors()){
            String errorName = br.getAllErrors().get(0).getDefaultMessage();
            log.error(errorName);
            return new BaseResponse<>(errorName);
        }

        try {
            return new BaseResponse<>(chatService.createChatRoom(chatRoomReqDto));
        } catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 유저의 채팅방 목록 조회
     */
    @GetMapping("/room/list/{user_id}")
    public BaseResponse<List<ChatRoomInfoDto>> getChatRoomList(@PathVariable("user_id") Long id){
        try {
            return new BaseResponse<>(chatService.getChatRoomList(id));
        } catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 채팅방 채팅 목록 조회
     */
    @GetMapping("/room/{room_id}")
    public BaseResponse<List<ChatRoomHistoryDto>> getChatRoomHistory(@PathVariable("room_id") String id){
        try {
            return new BaseResponse<>(chatService.getChatRoomHistory(id));
        } catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 채팅방 나가기
     */
    @DeleteMapping("/room/{room_id}/{user_id}")
    public BaseResponse<Boolean> deleteChatRoomUser(@PathVariable("room_id") String roomId, @PathVariable("user_id") Long userId){
        try {
            return new BaseResponse<>(chatService.deleteChatRoomUser(roomId, userId));
        } catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

}
