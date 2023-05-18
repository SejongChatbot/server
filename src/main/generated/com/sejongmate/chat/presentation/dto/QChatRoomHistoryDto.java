package com.sejongmate.chat.presentation.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.sejongmate.chat.presentation.dto.QChatRoomHistoryDto is a Querydsl Projection type for ChatRoomHistoryDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QChatRoomHistoryDto extends ConstructorExpression<ChatRoomHistoryDto> {

    private static final long serialVersionUID = -947360905L;

    public QChatRoomHistoryDto(com.querydsl.core.types.Expression<String> name, com.querydsl.core.types.Expression<String> profileUrl, com.querydsl.core.types.Expression<String> content, com.querydsl.core.types.Expression<java.time.LocalDateTime> createdAt) {
        super(ChatRoomHistoryDto.class, new Class<?>[]{String.class, String.class, String.class, java.time.LocalDateTime.class}, name, profileUrl, content, createdAt);
    }

}

