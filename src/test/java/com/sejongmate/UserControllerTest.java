package com.sejongmate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sejongmate.user.domain.Role;
import com.sejongmate.user.domain.User;
import com.sejongmate.user.domain.UserRepository;
import com.sejongmate.user.presentation.dto.UserLoginReqDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${spring.test.pw}")
    String pw;

    @Test
    @WithMockUser
    @Transactional
    public void user_get() throws Exception {
        String num = getMockUser();

        mockMvc.perform(
                        RestDocumentationRequestBuilders
                                .get("/api/users/{num}", num)
//                                .header(HttpHeaders.AUTHORIZATION, "Bearer")
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andDo(
                    document("user-get",
                            pathParameters(
                                    parameterWithName("num").description("학번")
                            ),
//                            requestHeaders(
//                                    headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer type Access token")
//                            ),
                            responseFields(
                                    fieldWithPath("isSuccess").description("Request 성공 여부"),
                                    fieldWithPath("code").description("응답 코드"),
                                    fieldWithPath("message").description("응답 메시지"),
                                    fieldWithPath("result.id").description("ID"),
                                    fieldWithPath("result.num").description("학번"),
                                    fieldWithPath("result.name").description("이름"),
                                    fieldWithPath("result.email").description("이메일"),
                                    fieldWithPath("result.department").description("학과"),
                                    fieldWithPath("result.contact").description("연락처"),
                                    fieldWithPath("result.profileUrl").description("프로필"),
                                    fieldWithPath("result.role").description("역할")
                            )
                    )
                ).andDo(
                        document("user-get",
                            Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                            Preprocessors.preprocessResponse(Preprocessors.prettyPrint())
                        )
                    );
    }


    @Test
    @Transactional
    public void user_signup() throws Exception {
        String content = objectMapper.writeValueAsString(new UserLoginReqDto("19011494", pw));
        mockMvc.perform(
                RestDocumentationRequestBuilders
                        .post("/api/users/signup")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(status().isOk())
                .andDo(
                        document("user-signup",
                                requestFields(
                                        fieldWithPath("num").description("학번"),
                                        fieldWithPath("password").description("비밀번호")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("Request 성공 여부"),
                                        fieldWithPath("code").description("응답 코드"),
                                        fieldWithPath("message").description("응답 메시지"),
                                        fieldWithPath("result.num").description("학번"),
                                        fieldWithPath("result.name").description("이름"),
                                        fieldWithPath("result.email").description("이메일"),
                                        fieldWithPath("result.department").description("학과"),
                                        fieldWithPath("result.contact").description("연락처"),
                                        fieldWithPath("result.role").description("역할")
                                        ))
                ).andDo(
                        document("user-signup",
                                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                                Preprocessors.preprocessResponse(Preprocessors.prettyPrint())
                        )
                );
    }

    @Test
    @WithMockUser
    @Transactional
    public void user_login() throws Exception {
        String num = getMockUser();
        String content = objectMapper.writeValueAsString(new UserLoginReqDto(num, "123456"));
        mockMvc.perform(
                        RestDocumentationRequestBuilders
                                .post("/api/users/login")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)

                ).andExpect(status().isOk())
                .andDo(
                        document("user-login",
                                requestFields(
                                        fieldWithPath("num").description("학번"),
                                        fieldWithPath("password").description("비밀번호")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("Request 성공 여부"),
                                        fieldWithPath("code").description("응답 코드"),
                                        fieldWithPath("message").description("응답 메시지"),
                                        fieldWithPath("result.accessToken").description("access token"),
                                        fieldWithPath("result.refreshToken").description("refresh token")
                                ))
                ).andDo(
                        document("user-login",
                                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                                Preprocessors.preprocessResponse(Preprocessors.prettyPrint())
                        )
                );
    }

    private String getMockUser() {
        String num = "123456";
        String pw = passwordEncoder.encode("123456");
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
        return num;
    }
}
