package com.sejongmate.user.presentation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserCreateReqDto {
    @NotNull(message = "학번을 입력하세요.")
    private String num;

    @NotNull(message = "비밀번호를 입력하세요.")
    private String password;
    private int role;

    @Builder
    public UserCreateReqDto(String num, String password, int role){
        this.num = num;
        this.password = password;
        this.role = role;
    }
}
