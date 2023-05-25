package com.sejongmate.post.infra;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sejongmate.common.BaseException;
import com.sejongmate.post.domain.*;
import com.sejongmate.post.presentation.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sejongmate.common.BaseResponseStatus.INVALID_POST_ID;
import static com.sejongmate.post.domain.QComment.comment;
import static com.sejongmate.post.domain.QPost.post;
import static com.sejongmate.post.domain.QPostScrap.postScrap;

@RequiredArgsConstructor
@Repository
public class PostQueryDao {
    private final JPAQueryFactory query;

    public List<PostListDto> getPostList() {
        return query
                .select(new QPostListDto(post.id, post.type, post.category, post.title, post.endAt))
                .from(post)
                .fetch();
    }

    public List<PostListDto> getPostListByCategory(Category category) {
        return query
                .select(new QPostListDto(post.id, post.type, post.category, post.title, post.endAt))
                .from(post)
                .where(post.category.eq(category))
                .fetch();
    }

    public List<PostListDto> getPostListByCategoryAndType(Category category, MeetingType type) {
        return query
                .select(new QPostListDto(post.id, post.type, post.category, post.title, post.endAt))
                .from(post)
                .where(post.category.eq(category), post.type.eq(type))
                .fetch();
    }


    public PostInfoDto getPostInfo(Long postId){
        List<PostInfoDto> postInfo = query
                .select(new QPostInfoDto(post.id, post.title, post.content, post.num, post.startAt, post.endAt))
                .from(post)
                .where(post.id.eq(postId))
                .fetch();

        if (postInfo.size() == 0)
            throw new BaseException(INVALID_POST_ID);

        return postInfo.get(0);
    }

    public List<CommentInfoDto> getCommentInfo(Long postId){
        return query
                .select(new QCommentInfoDto(comment.user.num, comment.content, comment.createdDate))
                .from(comment)
                .where(comment.post.id.eq(postId))
                .orderBy(comment.createdDate.asc())
                .fetch();
    }

    public Boolean isScraped(Long postId, Long userId){
        List<Long> scraped = query
                .select(postScrap.id)
                .from(postScrap)
                .where(postScrap.post.id.eq(postId), postScrap.user.id.eq(userId))
                .fetch();

        if (scraped.size() == 0) return false;
        else return true;
    }

    public List<PostListDto> getScrapedPost(Long userId){
        return query
                .select(new QPostListDto(post.id, post.type, post.category, post.title, post.endAt))
                .from(postScrap)
                .where(postScrap.user.id.eq(userId))
                .leftJoin(post).on(post.id.eq(postScrap.post.id))
                .fetch();
    }

    public Boolean deleteScrap(Long postId, Long userId){
        long result = query.delete(postScrap)
                .where(postScrap.post.id.eq(postId), postScrap.user.id.eq(userId))
                .execute();
        return result == 1? true : false;
    }
}
