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

    @GetMapping("/room/{id}")
    public BaseResponse<List<ChatRoomInfoDto>> getChatRoomList(@PathVariable("id") Long id){
        try {
            return new BaseResponse<>(chatService.getChatRoomList(id));
        } catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }
}
