package com.sejongmate.user.application;

import com.sejongmate.common.BaseException;
import com.sejongmate.user.application.util.WebDriverUtil;
import com.sejongmate.user.domain.Role;
import com.sejongmate.user.domain.User;
import com.sejongmate.user.domain.UserRepository;
import com.sejongmate.user.presentation.dto.UserCreateReqDto;
import com.sejongmate.user.presentation.dto.UserCreateResDto;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.sejongmate.common.BaseResponseStatus.USERS_DUPLICATED_NUM;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {
    private final UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public UserCreateResDto signUpUser(UserCreateReqDto userCreateReqDto) throws BaseException {
        validateDuplicateUser(userCreateReqDto.getNum());

        WebDriverUtil webDriverUtil = new WebDriverUtil(userCreateReqDto.getNum(), userCreateReqDto.getPassword());
        JSONObject userInfo = webDriverUtil.getUserInfoObj();
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

}
