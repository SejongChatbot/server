package com.sejongmate;

import com.sejongmate.authentication.presentation.dto.TokenDto;
import com.sejongmate.common.BaseException;
import com.sejongmate.user.application.UserService;
import com.sejongmate.user.domain.Role;
import com.sejongmate.user.domain.User;
import com.sejongmate.user.domain.UserRepository;
import com.sejongmate.user.presentation.dto.UserCreateReqDto;
import com.sejongmate.user.presentation.dto.UserLoginReqDto;
import com.sejongmate.user.presentation.dto.UserResDto;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.junit.Test;

import static org.junit.Assert.*;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test(expected = BaseException.class)
    public void 블랙보드_없는_유저_회원가입_시도() throws Exception {
        //given
        UserCreateReqDto userCreateReqDto = new UserCreateReqDto("123456", "123456", 0);

        //when
        userService.signUpUser(userCreateReqDto);

        //then
        fail("INVALID_USER_INFO 예외가 발생해야 한다.");
    }

    @Test
    public void 로그인() throws Exception {
        //given
        String num = "123456";
        String pw = "123456";
        String name = "홍길동";
        Role role = Role.STUDENT;

        userRepository.save(User.builder()
                .num(num)
                .password(passwordEncoder.encode(pw))
                .name(name)
                .role(role)
                .build());

        UserLoginReqDto userLoginReqDto = new UserLoginReqDto(num, pw);

        //when
        TokenDto tokenDto = userService.login(userLoginReqDto);

        //then
        assertNotNull(tokenDto.getAccessToken());
        assertNotNull(tokenDto.getRefreshToken());
    }

    @Test(expected = BaseException.class)
    public void 로그인_비밀번호_불일치() throws Exception {
        //given
        String num = "123456";
        String pw = "123456";
        String wrongPw = "12345";
        String name = "홍길동";
        Role role = Role.STUDENT;

        userRepository.save(User.builder()
                .num(num)
                .password(passwordEncoder.encode(pw))
                .name(name)
                .role(role)
                .build());

        UserLoginReqDto userLoginReqDto = new UserLoginReqDto(num, wrongPw);

        //when
        userService.login(userLoginReqDto);

        //then
        fail("INVALID_USER_PW 예외가 발생해야 한다.");
    }

}
