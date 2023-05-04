package com.sejongmate.user.application;

import com.sejongmate.authentication.domain.JwtTokenProvider;
import com.sejongmate.authentication.presentation.dto.TokenDto;
import com.sejongmate.common.BaseException;
import com.sejongmate.user.application.util.WebDriverUtil;
import com.sejongmate.user.domain.Role;
import com.sejongmate.user.domain.User;
import com.sejongmate.user.domain.UserRepository;
import com.sejongmate.user.presentation.dto.UserCreateReqDto;
import com.sejongmate.user.presentation.dto.UserCreateResDto;
import com.sejongmate.user.presentation.dto.UserLoginReqDto;
import com.sejongmate.user.presentation.dto.UserResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.sejongmate.common.BaseResponseStatus.*;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
@Log4j2
public class UserService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
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
    public TokenDto login(UserLoginReqDto userLoginReqDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLoginReqDto.getNum(),
                            userLoginReqDto.getPassword()
                    )
            );

            TokenDto tokenDto = new TokenDto(
                    jwtTokenProvider.createAccessToken(authentication),
                    jwtTokenProvider.createRefreshToken(authentication)
            );

            return tokenDto;

        }catch(BadCredentialsException e){
            log.error(INVALID_USER_PW.getMessage());
            throw new BaseException(INVALID_USER_PW);
        }
    }

    public UserResDto getUser(String num) {
        Optional<User> users = userRepository.findByNum(num);
        User user = users.orElseThrow(() -> {
            log.error(INVALID_USER_NUM.getMessage());
            return  new BaseException(INVALID_USER_NUM);
        });

        return UserResDto.from(user);
    }
}
