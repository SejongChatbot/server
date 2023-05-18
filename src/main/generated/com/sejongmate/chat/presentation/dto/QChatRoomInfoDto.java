package com.sejongmate.chat.presentation.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.sejongmate.chat.presentation.dto.QChatRoomInfoDto is a Querydsl Projection type for ChatRoomInfoDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QChatRoomInfoDto extends ConstructorExpression<ChatRoomInfoDto> {

    private static final long serialVersionUID = 277388037L;

    public QChatRoomInfoDto(com.querydsl.core.types.Expression<String> roomId, com.querydsl.core.types.Expression<String> name, com.querydsl.core.types.Expression<java.time.LocalDateTime> lastMessageAt, com.querydsl.core.types.Expression<String> lastMessage, com.querydsl.core.types.Expression<Integer> num) {
        super(ChatRoomInfoDto.class, new Class<?>[]{String.class, String.class, java.time.LocalDateTime.class, String.class, int.class}, roomId, name, lastMessageAt, lastMessage, num);
    }

}

