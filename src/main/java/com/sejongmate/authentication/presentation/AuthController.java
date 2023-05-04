package com.sejongmate.authentication.presentation;

import com.sejongmate.authentication.application.AuthService;
import com.sejongmate.authentication.presentation.dto.TokenDto;
import com.sejongmate.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/reissue")
    public BaseResponse<TokenDto> reissue(String refreshToken){
        return new BaseResponse<>(authService.reissueToken(refreshToken));
    }
}
