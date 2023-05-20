package com.sejongmate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sejongmate.chat.application.ChatService;
import com.sejongmate.chat.presentation.dto.ChatMessageReqDto;
import com.sejongmate.chat.presentation.dto.ChatRoomReqDto;
import com.sejongmate.chat.presentation.dto.ChatRoomResDto;
import com.sejongmate.user.domain.Role;
import com.sejongmate.user.domain.User;
import com.sejongmate.user.domain.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

import java.util.ArrayList;
import java.util.Arrays;

import static com.sejongmate.chat.domain.MessageType.MESSAGE;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class ChatControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ChatService chatService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @WithMockUser
    @Transactional
    @DisplayName("채팅방 생성")
    public void chat_room_post() throws Exception {
        getMockUser("111111", "111111");
        getMockUser("222222", "222222");

        String content = objectMapper.writeValueAsString(
                new ChatRoomReqDto("chatRoom1",
                        new ArrayList<>(Arrays.asList("111111", "222222"))
                )
        );

        mockMvc.perform(
                        RestDocumentationRequestBuilders
                                .post("/api/chat/room")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andDo(
                        document("chat-room-post",
                                requestFields(
                                        fieldWithPath("name").description("채팅방 이름"),
                                        fieldWithPath("participantNum").description("참가자 학번 리스트")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("Request 성공 여부"),
                                        fieldWithPath("code").description("응답 코드"),
                                        fieldWithPath("message").description("응답 메시지"),
                                        fieldWithPath("result.id").description("채팅방 ID"),
                                        fieldWithPath("result.createdAt").description("채팅방 생성시간")
                                ))
                ).andDo(
                        document("chat-room-post",
                                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                                Preprocessors.preprocessResponse(Preprocessors.prettyPrint())
                        )
                );
    }

    @Test
    @WithMockUser
    @Transactional
    @DisplayName("채팅 보내기")
    public void chat_message_post() throws Exception {
        getMockUser("111111", "111111");
        getMockUser("222222", "222222");

        ChatRoomResDto chatRoom = chatService.createChatRoom(new ChatRoomReqDto("chat room 1",
                        new ArrayList<>(Arrays.asList("111111", "222222"))
                )
        );

        String content = objectMapper.writeValueAsString(
                new ChatMessageReqDto(MESSAGE, chatRoom.getId(),"111111", "content")
        );

        mockMvc.perform(
                        RestDocumentationRequestBuilders
                                .post("/api/chat/message")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andDo(
                        document("chat-message-post",
                                requestFields(
                                        fieldWithPath("type").description("메시지 타입 (ENUM: MESSAGE, IMAGE)"),
                                        fieldWithPath("roomId").description("채팅방 ID"),
                                        fieldWithPath("senderNum").description("보낸 사람 학번"),
                                        fieldWithPath("content").description("메시지 내용")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("Request 성공 여부"),
                                        fieldWithPath("code").description("응답 코드"),
                                        fieldWithPath("message").description("응답 메시지"),
                                        fieldWithPath("result.id").description("채팅 메시지 ID"),
                                        fieldWithPath("result.createdAt").description("채팅 메시지 생성시간")
                                ))
                ).andDo(
                        document("chat-message-post",
                                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                                Preprocessors.preprocessResponse(Preprocessors.prettyPrint())
                        )
                );
    }

    @Test
    @WithMockUser
    @Transactional
    @DisplayName("채팅방 목록 조회")
    public void chat_room_list_get() throws Exception {
        Long userId1 = getMockUserForChatList();

        mockMvc.perform(
                        RestDocumentationRequestBuilders
                                .get("/api/chat/room/list/{user_id}", userId1)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andDo(
                        document("chat-room-list-get",
                                pathParameters(
                                        parameterWithName("user_id").description("유저 ID")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("Request 성공 여부"),
                                        fieldWithPath("code").description("응답 코드"),
                                        fieldWithPath("message").description("응답 메시지"),
                                        fieldWithPath("result.[].roomId").description("채팅방 ID"),
                                        fieldWithPath("result.[].name").description("채팅방 이름"),
                                        fieldWithPath("result.[].lastMessageAt").description("마지막으로 보낸 메시지 시간"),
                                        fieldWithPath("result.[].lastMessage").description("마지막으로 보낸 메시지 내용"),
                                        fieldWithPath("result.[].num").description("채팅방 인원")
                                )
                        )
                ).andDo(
                        document("chat-room-list-get",
                                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                                Preprocessors.preprocessResponse(Preprocessors.prettyPrint())
                        )
                );
    }



    @Test
    @WithMockUser
    @Transactional
    @DisplayName("채팅방 채팅 목록 조회")
    public void chat_room_message_get() throws Exception {
        Long userId = createMockUsers();
        String roomId = createMockRoomAndMessage();

        mockMvc.perform(
                        RestDocumentationRequestBuilders
                                .get("/api/chat/room/{room_id}", roomId)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andDo(
                        document("chat-room-message-get",
                                pathParameters(
                                        parameterWithName("room_id").description("채팅 ID")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("Request 성공 여부"),
                                        fieldWithPath("code").description("응답 코드"),
                                        fieldWithPath("message").description("응답 메시지"),
                                        fieldWithPath("result.[].type").description("메시지 타입 (ENUM: MESSAGE, IMAGE)"),
                                        fieldWithPath("result.[].name").description("보낸 사람 이름"),
                                        fieldWithPath("result.[].profileUrl").description("보낸 사람 프로필"),
                                        fieldWithPath("result.[].content").description("메시지 내용"),
                                        fieldWithPath("result.[].createdAt").description("메시지 보낸 시간")
                                )
                        )
                ).andDo(
                        document("chat-room-message-get",
                                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                                Preprocessors.preprocessResponse(Preprocessors.prettyPrint())
                        )
                );
    }

    @Test
    @WithMockUser
    @Transactional
    @DisplayName("채팅방 나가기")
    public void chat_room_user_delete() throws Exception {
        Long userId = createMockUsers();
        String roomId = createMockRoomAndMessage();

        mockMvc.perform(
                        RestDocumentationRequestBuilders
                                .delete("/api/chat/room/{room_id}/{user_id}", roomId, userId)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andDo(
                        document("chat-room-user-delete",
                                pathParameters(
                                        parameterWithName("room_id").description("채팅방 ID"),
                                        parameterWithName("user_id").description("유저 ID")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("Request 성공 여부"),
                                        fieldWithPath("code").description("응답 코드"),
                                        fieldWithPath("message").description("응답 메시지"),
                                        fieldWithPath("result").description("채팅방 나가기 성공 여부")
                                )
                        )
                ).andDo(
                        document("chat-room-user-delete",
                                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                                Preprocessors.preprocessResponse(Preprocessors.prettyPrint())
                        )
                );
    }

    private Long getMockUserForChatList() {
        Long userId1 = createMockUsers();

        ChatRoomResDto chatRoom1 = chatService.createChatRoom(new ChatRoomReqDto("chat room 1",
                        new ArrayList<>(Arrays.asList("111111", "222222", "333333"))
                )
        );
        ChatRoomResDto chatRoom2 = chatService.createChatRoom(new ChatRoomReqDto("chat room 2",
                        new ArrayList<>(Arrays.asList("111111", "222222"))
                )
        );
        ChatRoomResDto chatRoom3 = chatService.createChatRoom(new ChatRoomReqDto("chat room 2",
                        new ArrayList<>(Arrays.asList("111111", "333333"))
                )
        );

        chatService.sendMessage(new ChatMessageReqDto(MESSAGE, chatRoom1.getId(),"222222", "content 1"));
        chatService.sendMessage(new ChatMessageReqDto(MESSAGE, chatRoom2.getId(),"111111", "content 2"));
        chatService.sendMessage(new ChatMessageReqDto(MESSAGE, chatRoom3.getId(),"333333", "content 3"));

        return userId1;
    }

    private String createMockRoomAndMessage() {
        ChatRoomResDto chatRoom1 = chatService.createChatRoom(new ChatRoomReqDto("chat room 1",
                        new ArrayList<>(Arrays.asList("111111", "222222", "333333"))
                )
        );

        chatService.sendMessage(new ChatMessageReqDto(MESSAGE, chatRoom1.getId(),"222222", "content 1"));
        chatService.sendMessage(new ChatMessageReqDto(MESSAGE, chatRoom1.getId(),"111111", "content 2"));
        chatService.sendMessage(new ChatMessageReqDto(MESSAGE, chatRoom1.getId(),"333333", "content 3"));

        return chatRoom1.getId();
    }

    private Long createMockUsers(){
        Long userId1 = getMockUser("111111", "111111");
        Long userId2 = getMockUser("222222", "222222");
        Long userId3 = getMockUser("333333", "333333");

        return userId1;
    }
    private Long getMockUser(String num, String pw) {
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
}
