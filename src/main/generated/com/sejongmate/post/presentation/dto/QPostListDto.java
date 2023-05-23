package com.sejongmate.post.presentation.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.sejongmate.post.presentation.dto.QPostListDto is a Querydsl Projection type for PostListDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QPostListDto extends ConstructorExpression<PostListDto> {

    private static final long serialVersionUID = 1309853536L;

    public QPostListDto(com.querydsl.core.types.Expression<Long> postId, com.querydsl.core.types.Expression<com.sejongmate.post.domain.MeetingType> meetingType, com.querydsl.core.types.Expression<String> title, com.querydsl.core.types.Expression<java.time.LocalDateTime> endAt) {
        super(PostListDto.class, new Class<?>[]{long.class, com.sejongmate.post.domain.MeetingType.class, String.class, java.time.LocalDateTime.class}, postId, meetingType, title, endAt);
    }

}

