package com.sejongmate.post.presentation;

import com.sejongmate.chat.presentation.dto.ChatMessageReqDto;
import com.sejongmate.chat.presentation.dto.ChatMessageResDto;
import com.sejongmate.common.BaseException;
import com.sejongmate.common.BaseResponse;
import com.sejongmate.post.application.PostService;
import com.sejongmate.post.presentation.dto.CommentCreateReqDto;
import com.sejongmate.post.presentation.dto.CommentCreateResDto;
import com.sejongmate.post.presentation.dto.PostCreateReqDto;
import com.sejongmate.post.presentation.dto.PostCreateResDto;
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
@RequestMapping("/api/post")
@Log4j2
public class PostController {
    private final PostService postService;

    @PostMapping
    public BaseResponse<PostCreateResDto> createPost(@Valid @RequestBody PostCreateReqDto postCreateReqDto, BindingResult br) {
        if (br.hasErrors()){
            String errorName = br.getAllErrors().get(0).getDefaultMessage();
            log.error(errorName);
            return new BaseResponse<>(errorName);
        }

        try {
            return new BaseResponse<>(postService.createPost(postCreateReqDto));
        } catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PostMapping("/comment")
    public BaseResponse<CommentCreateResDto> createPost(@Valid @RequestBody CommentCreateReqDto commentCreateReqDto, BindingResult br) {
        if (br.hasErrors()){
            String errorName = br.getAllErrors().get(0).getDefaultMessage();
            log.error(errorName);
            return new BaseResponse<>(errorName);
        }

        try {
            return new BaseResponse<>(postService.createComment(commentCreateReqDto));
        } catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }
}
