package com.sejongmate.post.presentation;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.sejongmate.chat.presentation.dto.ChatMessageReqDto;
import com.sejongmate.chat.presentation.dto.ChatMessageResDto;
import com.sejongmate.common.BaseException;
import com.sejongmate.common.BaseResponse;
import com.sejongmate.post.application.PostService;
import com.sejongmate.post.domain.Category;
import com.sejongmate.post.domain.MeetingType;
import com.sejongmate.post.presentation.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/post")
@Log4j2
public class PostController {
    private final PostService postService;

    /**
     * 글 생성
     * */
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

    /**
     * 댓글 생성
     * */
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

    /**
     * 스크랩 하기
     * */
    @PostMapping("/scrap")
    public BaseResponse<ScrapCreateResDto> createPost(@Valid @RequestBody ScrapCreateReqDto scrapCreateReqDto, BindingResult br) {
        if (br.hasErrors()){
            String errorName = br.getAllErrors().get(0).getDefaultMessage();
            log.error(errorName);
            return new BaseResponse<>(errorName);
        }

        try {
            return new BaseResponse<>(postService.createScrap(scrapCreateReqDto));
        } catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 전체 글 리스트 조회
     * */
    @GetMapping("/list")
    public BaseResponse<List<PostListDto>> getPostList(){
        try {
            return new BaseResponse<>(postService.getPostList());
        } catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 카테고리 별 글 리스트 조회
     * */
    @GetMapping("/list/{category}")
    public BaseResponse<List<PostListDto>> getPostListByCategory(@PathVariable("category") Category category){
        try {
            return new BaseResponse<>(postService.getPostListByCategory(category));
        } catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 카테고리 & 대면 여부 별 글 리스트 조회
     * */
    @GetMapping("/list/{category}/{type}")
    public BaseResponse<List<PostListDto>> getPostListByCategoryAndType(@PathVariable("category") Category category, @PathVariable("type") MeetingType type){
        try {
            return new BaseResponse<>(postService.getPostListByCategoryAndType(category, type));
        } catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 글 상세 조회
     * */
    @GetMapping("/{post_id}/user/{user_id}")
    public BaseResponse<PostResDto> getPost(@PathVariable("post_id") Long postId, @PathVariable("user_id") Long userId){
        try {
            return new BaseResponse<>(postService.getPost(postId, userId));
        } catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 스크랩 글 조회
     * */
    @GetMapping("/scrap/{user_id}")
    public BaseResponse<List<PostListDto>> getScrapedPost(@PathVariable("user_id") Long userId){
        try {
            return new BaseResponse<>(postService.getScrapedPost(userId));
        } catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 스크랩 삭제
     * */
    @DeleteMapping("/scrap/{post_id}/user/{user_id}")
    public BaseResponse<Boolean> deleteScrap(@PathVariable("user_id") Long userId, @PathVariable("post_id") Long postId){
        try {
            return new BaseResponse<>(postService.deleteScrap(userId, postId));
        } catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }
}
