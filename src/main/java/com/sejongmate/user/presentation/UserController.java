package com.sejongmate.user.presentation;

import com.sejongmate.authentication.presentation.dto.TokenDto;
import com.sejongmate.common.BaseException;
import com.sejongmate.common.BaseResponse;
import com.sejongmate.user.application.UserService;
import com.sejongmate.user.presentation.dto.UserCreateReqDto;
import com.sejongmate.user.presentation.dto.UserCreateResDto;
import com.sejongmate.user.presentation.dto.UserLoginReqDto;
import com.sejongmate.user.presentation.dto.UserResDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/login")
    public BaseResponse<TokenDto> login(@Valid @RequestBody UserLoginReqDto userLoginReqDto, BindingResult br) throws BaseException {

        if (br.hasErrors()){
            String errorName = br.getAllErrors().get(0).getDefaultMessage();
            log.error(errorName);
            return new BaseResponse<>(errorName);
        }

        try {
            return new BaseResponse<>(userService.login(userLoginReqDto));
        } catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/{num}")
    public BaseResponse<UserResDto> getUser(@PathVariable("num") String num) {
        try {
            return new BaseResponse<>(userService.getUser(num));
        } catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }
}
