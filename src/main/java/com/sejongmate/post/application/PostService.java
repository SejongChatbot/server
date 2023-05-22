package com.sejongmate.post.application;

import com.sejongmate.common.BaseException;
import com.sejongmate.post.domain.Comment;
import com.sejongmate.post.domain.CommentRepository;
import com.sejongmate.post.domain.Post;
import com.sejongmate.post.domain.PostRepository;
import com.sejongmate.post.presentation.dto.CommentCreateReqDto;
import com.sejongmate.post.presentation.dto.CommentCreateResDto;
import com.sejongmate.post.presentation.dto.PostCreateReqDto;
import com.sejongmate.post.presentation.dto.PostCreateResDto;
import com.sejongmate.user.domain.User;
import com.sejongmate.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.sejongmate.common.BaseResponseStatus.INVALID_USER_ID;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
@Log4j2
public class PostService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public PostCreateResDto createPost(PostCreateReqDto postCreateReqDto) {
        User user = userRepository.findById(postCreateReqDto.getUserId())
                .orElseThrow(() -> {
                    log.error(INVALID_USER_ID.getMessage());
                    return new BaseException(INVALID_USER_ID);
                });

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
        User user = userRepository.findById(commentCreateReqDto.getUserId())
                .orElseThrow(() -> {
                    log.error(INVALID_USER_ID.getMessage());
                    return new BaseException(INVALID_USER_ID);
                });

        Post post = postRepository.findById(commentCreateReqDto.getPostId())
                .orElseThrow(() -> {
                    log.error(INVALID_USER_ID.getMessage());
                    return new BaseException(INVALID_USER_ID);
                });

        Comment comment = Comment.builder()
                .user(user)
                .post(post)
                .content(commentCreateReqDto.getContent())
                .build();

        commentRepository.save(comment);
        return CommentCreateResDto.from(comment);
    }
}
