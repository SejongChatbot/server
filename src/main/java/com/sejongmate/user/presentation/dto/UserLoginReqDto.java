package com.sejongmate.user.presentation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserLoginReqDto {
    @NotNull(message = "학번을 입력하세요.")
    private String num;

    @NotNull(message = "비밀번호를 입력하세요.")
    private String password;

    @Builder
    public UserLoginReqDto(String num, String password){
        this.num = num;
        this.password = password;
    }
}
