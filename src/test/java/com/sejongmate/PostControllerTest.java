package com.sejongmate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sejongmate.chat.presentation.dto.ChatRoomReqDto;
import com.sejongmate.post.application.PostService;
import com.sejongmate.post.domain.Category;
import com.sejongmate.post.domain.MeetingType;
import com.sejongmate.post.presentation.dto.CommentCreateReqDto;
import com.sejongmate.post.presentation.dto.PostCreateReqDto;
import com.sejongmate.post.presentation.dto.ScrapCreateReqDto;
import com.sejongmate.user.domain.Role;
import com.sejongmate.user.domain.User;
import com.sejongmate.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PostControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PostService postService;
    @Autowired
    PasswordEncoder passwordEncoder;

    Long mockUserId;
    Long mockPostId;
    Long mockCommentId;

    @BeforeAll
    void setUp(){
        mockUserId = getMockUserId("111111", "111111");
        mockPostId = getMockPostId(mockUserId);
        mockCommentId = getMockCommentId(mockUserId, mockPostId);
    }

    @Test
    @WithMockUser
    @Transactional
    public void create_post() throws Exception {
        String content = objectMapper.writeValueAsString(
                new PostCreateReqDto(mockUserId, Category.ETC, MeetingType.ON,
                        "title", "content", LocalDateTime.of(2023, 05, 10, 00, 00),
                        LocalDateTime.of(2023, 05, 20, 00, 00), 6
                )
        );

        mockMvc.perform(
                        RestDocumentationRequestBuilders
                                .post("/api/post")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andDo(
                        document("create-post",
                                requestFields(
                                        fieldWithPath("userId").description("유저 ID"),
                                        fieldWithPath("category").description("스터디 카테고리 [ ENUN : LANGUAGE, JOB, OFFICIAL, PROGRAMMING, ETC ]"),
                                        fieldWithPath("type").description("대면 여부 [ ENUM : ON, OFF, UNDEFINED ]"),
                                        fieldWithPath("title").description("글 제목"),
                                        fieldWithPath("content").description("글 내용"),
                                        fieldWithPath("startAt").description("모집 시작일"),
                                        fieldWithPath("endAt").description("모집 마감일"),
                                        fieldWithPath("num").description("모집 인원")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("Request 성공 여부"),
                                        fieldWithPath("code").description("응답 코드"),
                                        fieldWithPath("message").description("응답 메시지"),
                                        fieldWithPath("result.id").description("글 ID"),
                                        fieldWithPath("result.createdDate").description("글 생성시간")
                                ))
                ).andDo(
                        document("create-post",
                                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                                Preprocessors.preprocessResponse(Preprocessors.prettyPrint())
                        )
                );
    }

    @Test
    @WithMockUser
    @Transactional
    public void create_comment() throws Exception{
        String content = objectMapper.writeValueAsString(
                new CommentCreateReqDto(mockUserId, mockPostId, "comment content")
        );

        mockMvc.perform(
                        RestDocumentationRequestBuilders
                                .post("/api/post/comment")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andDo(
                        document("create-comment",
                                requestFields(
                                        fieldWithPath("userId").description("유저 ID"),
                                        fieldWithPath("postId").description("글 ID"),
                                        fieldWithPath("content").description("댓글 내용")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("Request 성공 여부"),
                                        fieldWithPath("code").description("응답 코드"),
                                        fieldWithPath("message").description("응답 메시지"),
                                        fieldWithPath("result.id").description("댓글 ID"),
                                        fieldWithPath("result.createdDate").description("댓글 생성시간")
                                ))
                ).andDo(
                        document("create-comment",
                                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                                Preprocessors.preprocessResponse(Preprocessors.prettyPrint())
                        )
                );
    }

    @Test
    @WithMockUser
    @Transactional
    public void create_scrap() throws Exception {
        String content = objectMapper.writeValueAsString(
                new ScrapCreateReqDto(mockUserId, mockPostId)
        );

        mockMvc.perform(
                        RestDocumentationRequestBuilders
                                .post("/api/post/scrap")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andDo(
                        document("create-scrap",
                                requestFields(
                                        fieldWithPath("userId").description("유저 ID"),
                                        fieldWithPath("postId").description("글 ID")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("Request 성공 여부"),
                                        fieldWithPath("code").description("응답 코드"),
                                        fieldWithPath("message").description("응답 메시지"),
                                        fieldWithPath("result.id").description("스크랩 ID")
                                ))
                ).andDo(
                        document("create-scrap",
                                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                                Preprocessors.preprocessResponse(Preprocessors.prettyPrint())
                        )
                );
    }

    @Test
    @WithMockUser
    public void get_post_list() throws Exception {

        mockMvc.perform(
                        RestDocumentationRequestBuilders
                                .get("/api/post/list")
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andDo(
                        document("get-post-list",
                                responseFields(
                                        fieldWithPath("isSuccess").description("Request 성공 여부"),
                                        fieldWithPath("code").description("응답 코드"),
                                        fieldWithPath("message").description("응답 메시지"),
                                        fieldWithPath("result.[].postId").description("글 ID"),
                                        fieldWithPath("result.[].meetingType").description("대면 여부 [ ENUM : ON, OFF, UNDEFINED ]"),
                                        fieldWithPath("result.[].title").description("글 제목"),
                                        fieldWithPath("result.[].endAt").description("모집 마감일")
                                )
                        )
                ).andDo(
                        document("get-post-list",
                                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                                Preprocessors.preprocessResponse(Preprocessors.prettyPrint())
                        )
                );
    }

    @Test
    @WithMockUser
    public void get_post_list_by_category() throws Exception {
        Category category = Category.JOB;

        mockMvc.perform(
                        RestDocumentationRequestBuilders
                                .get("/api/post/list/{category}", category)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andDo(
                        document("get-post-list-by-category",
                                pathParameters(
                                        parameterWithName("category").description("스터디 카테고리 [ ENUN : LANGUAGE, JOB, OFFICIAL, PROGRAMMING, ETC ]")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("Request 성공 여부"),
                                        fieldWithPath("code").description("응답 코드"),
                                        fieldWithPath("message").description("응답 메시지"),
                                        fieldWithPath("result.[].postId").description("글 ID"),
                                        fieldWithPath("result.[].meetingType").description("대면 여부 [ ENUM : ON, OFF, UNDEFINED ]"),
                                        fieldWithPath("result.[].title").description("글 제목"),
                                        fieldWithPath("result.[].endAt").description("모집 마감일")
                                )
                        )
                ).andDo(
                        document("get-post-list-by-category",
                                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                                Preprocessors.preprocessResponse(Preprocessors.prettyPrint())
                        )
                );
    }

    @Test
    @WithMockUser
    public void get_post_list_by_category_and_type() throws Exception {
        Category category = Category.JOB;
        MeetingType type = MeetingType.ON;

        mockMvc.perform(
                        RestDocumentationRequestBuilders
                                .get("/api/post/list/{category}/{type}", category, type)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andDo(
                        document("get-post-list-by-category-and-type",
                                pathParameters(
                                        parameterWithName("category").description("스터디 카테고리 [ ENUN : LANGUAGE, JOB, OFFICIAL, PROGRAMMING, ETC ]"),
                                        parameterWithName("type").description("대면 여부 [ ENUM : ON, OFF, UNDEFINED ]")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("Request 성공 여부"),
                                        fieldWithPath("code").description("응답 코드"),
                                        fieldWithPath("message").description("응답 메시지"),
                                        fieldWithPath("result.[].postId").description("글 ID"),
                                        fieldWithPath("result.[].meetingType").description("대면 여부 [ ENUM : ON, OFF, UNDEFINED ]"),
                                        fieldWithPath("result.[].title").description("글 제목"),
                                        fieldWithPath("result.[].endAt").description("모집 마감일")
                                )
                        )
                ).andDo(
                        document("get-post-list-by-category-and-type",
                                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                                Preprocessors.preprocessResponse(Preprocessors.prettyPrint())
                        )
                );
    }

    @Test
    @WithMockUser
    public void get_post() throws Exception {

        mockMvc.perform(
                        RestDocumentationRequestBuilders
                                .get("/api/post/{post_id}/user/{user_id}", mockPostId, mockUserId)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andDo(
                        document("get-post",
                                pathParameters(
                                        parameterWithName("post_id").description("글 ID"),
                                        parameterWithName("user_id").description("유저 ID")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("Request 성공 여부"),
                                        fieldWithPath("code").description("응답 코드"),
                                        fieldWithPath("message").description("응답 메시지"),
                                        fieldWithPath("result.postInfoDto.postId").description("글 ID"),
                                        fieldWithPath("result.postInfoDto.title").description("글 제목"),
                                        fieldWithPath("result.postInfoDto.content").description("글 내용"),
                                        fieldWithPath("result.postInfoDto.num").description("모집 인원"),
                                        fieldWithPath("result.postInfoDto.startAt").description("모집 시작일"),
                                        fieldWithPath("result.postInfoDto.endAt").description("모집 마감일"),
                                        fieldWithPath("result.commentInfoDtos.[].num").description("댓글 작성자 학번"),
                                        fieldWithPath("result.commentInfoDtos.[].content").description("댓글 내용"),
                                        fieldWithPath("result.commentInfoDtos.[].createdDate").description("댓글 생성시간"),
                                        fieldWithPath("result.isScraped").description("스크랩 여부")
                                )
                        )
                ).andDo(
                        document("get-post",
                                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                                Preprocessors.preprocessResponse(Preprocessors.prettyPrint())
                        )
                );
    }

    @Test
    @WithMockUser
    public void get_scraped_post() throws Exception {

        mockMvc.perform(
                        RestDocumentationRequestBuilders
                                .get("/api/post/scrap/{user_id}", mockUserId)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andDo(
                        document("get-scraped-post",
                                pathParameters(
                                        parameterWithName("user_id").description("유저 ID")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("Request 성공 여부"),
                                        fieldWithPath("code").description("응답 코드"),
                                        fieldWithPath("message").description("응답 메시지"),
                                        fieldWithPath("result.[].postId").description("글 ID"),
                                        fieldWithPath("result.[].meetingType").description("대면 여부 [ ENUM : ON, OFF, UNDEFINED ]"),
                                        fieldWithPath("result.[].title").description("글 제목"),
                                        fieldWithPath("result.[].endAt").description("모집 마감일")
                                )
                        )
                ).andDo(
                        document("get-scraped-post",
                                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                                Preprocessors.preprocessResponse(Preprocessors.prettyPrint())
                        )
                );
    }

    @Test
    @WithMockUser
    public void delete_scrap() throws Exception {
        mockMvc.perform(
                        RestDocumentationRequestBuilders
                                .delete("/api/post/scrap/{post_id}/user/{user_id}", mockPostId, mockUserId)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andDo(
                        document("delete-scrap",
                                pathParameters(
                                        parameterWithName("post_id").description("글 ID"),
                                        parameterWithName("user_id").description("유저 ID")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("Request 성공 여부"),
                                        fieldWithPath("code").description("응답 코드"),
                                        fieldWithPath("message").description("응답 메시지"),
                                        fieldWithPath("result").description("스크랩 삭제 성공 여부")
                                )
                        )
                ).andDo(
                        document("delete-scrap",
                                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                                Preprocessors.preprocessResponse(Preprocessors.prettyPrint())
                        )
                );
    }

    private Long getMockUserId(String num, String pw) {
        pw = passwordEncoder.encode(pw);
        String name = "홍길동";
        String email = "aaa@sju.ac.kr";
        String department = "컴퓨터공학과";
        String contact = "000-0000-0000";
        String profileUrl = "www.abc.com";
        Role role = Role.STUDENT;

        userRepository.save(User.builder()
                .num(num)
                .password(pw)
                .name(name)
                .email(email)
                .department(department)
                .contact(contact)
                .profileUrl(profileUrl)
                .role(role)
                .build());

        return userRepository.findByNum(num).get().getId();
    }


    private Long getMockCommentId(Long userId, Long postId) {
        Long commentId = postService.createComment(
                        new CommentCreateReqDto(userId, postId, "test comment content 1"))
                .getId();
        postService.createComment(
                        new CommentCreateReqDto(userId, postId, "test comment content 2"))
                .getId();

        return commentId;
    }

    private Long getMockPostId(Long userId) {
        Long postId = postService.createPost(new PostCreateReqDto(userId, Category.ETC, MeetingType.ON,
                "test title 1", "test content 1", LocalDateTime.of(2023, 05, 10, 00, 00),
                LocalDateTime.of(2023, 05, 20, 00, 00), 6
        )).getId();

        Long postId1 = postService.createPost(new PostCreateReqDto(userId, Category.JOB, MeetingType.ON,
                "test title 2", "test content 2", LocalDateTime.of(2023, 05, 10, 00, 00),
                LocalDateTime.of(2023, 05, 20, 00, 00), 6
        )).getId();

        postService.createPost(new PostCreateReqDto(userId, Category.JOB, MeetingType.OFF,
                "test title 3", "test content 3", LocalDateTime.of(2023, 05, 10, 00, 00),
                LocalDateTime.of(2023, 05, 20, 00, 00), 6
        )).getId();

        postService.createPost(new PostCreateReqDto(userId, Category.JOB, MeetingType.ON,
                "test title 4", "test content 4", LocalDateTime.of(2023, 05, 10, 00, 00),
                LocalDateTime.of(2023, 05, 20, 00, 00), 6
        )).getId();

        postService.createScrap(new ScrapCreateReqDto(userId, postId));
        postService.createScrap(new ScrapCreateReqDto(userId, postId1));


        return postId;
    }

}