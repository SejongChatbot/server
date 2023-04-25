package com.sejongmate.user.presentation;

import com.sejongmate.common.BaseException;
import com.sejongmate.common.BaseResponse;
import com.sejongmate.common.BaseResponseStatus;
import com.sejongmate.user.application.UserService;
import com.sejongmate.user.presentation.dto.UserCreateReqDto;
import com.sejongmate.user.presentation.dto.UserCreateResDto;
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
@RequestMapping("/api/users")
@Log4j2
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public BaseResponse<UserCreateResDto> signUp(@Valid @RequestBody UserCreateReqDto userCreateReqDto, BindingResult br) {

        // 형식적 validation
        if (br.hasErrors()){
            String errorName = br.getAllErrors().get(0).getDefaultMessage(); //dto에 선언한 에러
            log.error(errorName);
            return new BaseResponse<>(errorName);
        }

        try {
            return new BaseResponse<>(userService.signUpUser(userCreateReqDto));
        } catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }
}
