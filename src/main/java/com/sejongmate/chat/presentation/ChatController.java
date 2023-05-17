package com.sejongmate.chat.presentation;

import com.sejongmate.chat.application.ChatService;
import com.sejongmate.chat.presentation.dto.ChatMessageReqDto;
import com.sejongmate.chat.presentation.dto.ChatMessageResDto;
import com.sejongmate.chat.presentation.dto.ChatRoomReqDto;
import com.sejongmate.chat.presentation.dto.ChatRoomResDto;
import com.sejongmate.common.BaseException;
import com.sejongmate.common.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
