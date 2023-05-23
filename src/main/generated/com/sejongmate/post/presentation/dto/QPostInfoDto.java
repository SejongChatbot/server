package com.sejongmate.post.presentation.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.sejongmate.post.presentation.dto.QPostInfoDto is a Querydsl Projection type for PostInfoDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QPostInfoDto extends ConstructorExpression<PostInfoDto> {

    private static final long serialVersionUID = -1221666480L;

    public QPostInfoDto(com.querydsl.core.types.Expression<Long> postId, com.querydsl.core.types.Expression<String> title, com.querydsl.core.types.Expression<String> content, com.querydsl.core.types.Expression<Integer> num, com.querydsl.core.types.Expression<java.time.LocalDateTime> startAt, com.querydsl.core.types.Expression<java.time.LocalDateTime> endAt) {
        super(PostInfoDto.class, new Class<?>[]{long.class, String.class, String.class, int.class, java.time.LocalDateTime.class, java.time.LocalDateTime.class}, postId, title, content, num, startAt, endAt);
    }

}

