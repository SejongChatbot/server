package com.sejongmate.user.application;

import com.sejongmate.common.BaseException;
import com.sejongmate.user.application.util.JwtService;
import com.sejongmate.user.application.util.WebDriverUtil;
import com.sejongmate.user.domain.Role;
import com.sejongmate.user.domain.User;
import com.sejongmate.user.domain.UserRepository;
import com.sejongmate.user.presentation.dto.UserCreateReqDto;
import com.sejongmate.user.presentation.dto.UserCreateResDto;
import com.sejongmate.user.presentation.dto.UserLoginReqDto;
import com.sejongmate.user.presentation.dto.UserLoginResDto;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.sejongmate.common.BaseResponseStatus.*;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final WebDriverUtil webDriverUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public UserCreateResDto signUpUser(UserCreateReqDto userCreateReqDto) throws BaseException {
        validateDuplicateUser(userCreateReqDto.getNum());

        webDriverUtil.chrome();
        JSONObject userInfo = webDriverUtil.getUserInfoObj(userCreateReqDto.getNum(), userCreateReqDto.getPassword());
        User user = User.builder()
                .num(userCreateReqDto.getNum())
                .password(passwordEncoder.encode(userCreateReqDto.getPassword()))
                .role(userCreateReqDto.getRole() == 0 ? Role.STUDENT : Role.PROFESSOR)
                .name(userInfo.get("givenName").toString())
                .email(userInfo.get("emailAddress").toString())
                .profileUrl(null)
                .department(userInfo.get("department").toString())
                .contact(userInfo.get("mobilePhone").toString())
                .build();

        userRepository.save(user);

        return UserCreateResDto.from(user);
    }


    // 유저 중복 확인
    private void validateDuplicateUser(String num) throws BaseException {
        Optional<User> findUsers = userRepository.findByNum(num);
        if (!findUsers.isEmpty()){
            throw new BaseException(USERS_DUPLICATED_NUM);
        }
    }

    @Transactional
    public UserLoginResDto login(UserLoginReqDto userLoginReqDto) throws BaseException {
        Optional<User> users = userRepository.findByNum(userLoginReqDto.getNum());
        User user = users.orElseThrow(() -> new BaseException(INVALID_USER_NUM));

        if(!passwordEncoder.matches(userLoginReqDto.getPassword(), user.getPassword())) {
            throw new BaseException(INVALID_USER_PW);
        }

        String jwt = jwtService.createJwtToken(user.getNum());

        return UserLoginResDto.from(user, jwt);
    }
}
