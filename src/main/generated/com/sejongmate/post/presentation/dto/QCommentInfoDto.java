package com.sejongmate.post.presentation.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.sejongmate.post.presentation.dto.QCommentInfoDto is a Querydsl Projection type for CommentInfoDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QCommentInfoDto extends ConstructorExpression<CommentInfoDto> {

    private static final long serialVersionUID = 1329609427L;

    public QCommentInfoDto(com.querydsl.core.types.Expression<String> num, com.querydsl.core.types.Expression<String> content, com.querydsl.core.types.Expression<java.time.LocalDateTime> createdDate) {
        super(CommentInfoDto.class, new Class<?>[]{String.class, String.class, java.time.LocalDateTime.class}, num, content, createdDate);
    }

}

