package com.sejongmate.post.infra;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sejongmate.post.domain.Category;
import com.sejongmate.post.domain.MeetingType;
import com.sejongmate.post.domain.QPost;
import com.sejongmate.post.presentation.dto.PostListDto;
import com.sejongmate.post.presentation.dto.QPostListDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sejongmate.post.domain.QPost.post;

@RequiredArgsConstructor
@Repository
public class PostQueryDao {
    private final JPAQueryFactory query;

    public List<PostListDto> getPostList() {
        return query
                .select(new QPostListDto(post.id, post.type, post.title, post.endAt))
                .from(post)
                .fetch();
    }

    public List<PostListDto> getPostListByCategory(Category category) {
        return query
                .select(new QPostListDto(post.id, post.type, post.title, post.endAt))
                .from(post)
                .where(post.category.eq(category))
                .fetch();
    }
}
