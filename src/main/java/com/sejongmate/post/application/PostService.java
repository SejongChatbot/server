package com.sejongmate.post.application;

import com.sejongmate.common.BaseException;
import com.sejongmate.post.domain.*;
import com.sejongmate.post.presentation.dto.*;
import com.sejongmate.user.domain.User;
import com.sejongmate.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.sejongmate.common.BaseResponseStatus.INVALID_POST_ID;
import static com.sejongmate.common.BaseResponseStatus.INVALID_USER_ID;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
@Log4j2
public class PostService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final PostScrapRepository postScrapRepository;

    @Transactional
    public PostCreateResDto createPost(PostCreateReqDto postCreateReqDto) {
        User user = findUserById(postCreateReqDto.getUserId());

        Post post = Post.builder()
                .user(user)
                .category(postCreateReqDto.getCategory())
                .type(postCreateReqDto.getType())
                .title(postCreateReqDto.getTitle())
                .content(postCreateReqDto.getContent())
                .startAt(postCreateReqDto.getStartAt())
                .endAt(postCreateReqDto.getEndAt())
                .num(postCreateReqDto.getNum())
                .build();

        postRepository.save(post);
        return PostCreateResDto.from(post);
    }

    public CommentCreateResDto createComment(CommentCreateReqDto commentCreateReqDto) {
        User user = findUserById(commentCreateReqDto.getUserId());
        Post post = findPostById(commentCreateReqDto.getPostId());

        Comment comment = Comment.builder()
                .user(user)
                .post(post)
                .content(commentCreateReqDto.getContent())
                .build();

        commentRepository.save(comment);
        return CommentCreateResDto.from(comment);
    }

    public ScrapCreateResDto createScrap(ScrapCreateReqDto scrapCreateReqDto) {
        User user = findUserById(scrapCreateReqDto.getUserId());
        Post post = findPostById(scrapCreateReqDto.getPostId());

        PostScrap scrap = PostScrap.builder()
                .user(user)
                .post(post)
                .build();

        postScrapRepository.save(scrap);
        return ScrapCreateResDto.from(scrap);
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error(INVALID_USER_ID.getMessage());
                    return new BaseException(INVALID_USER_ID);
                });
    }

    private Post findPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> {
                    log.error(INVALID_POST_ID.getMessage());
                    return new BaseException(INVALID_POST_ID);
                });
    }
}
