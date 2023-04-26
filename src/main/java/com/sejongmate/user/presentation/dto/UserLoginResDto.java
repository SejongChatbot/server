package com.sejongmate.user.presentation.dto;

import com.sejongmate.user.domain.Role;
import com.sejongmate.user.domain.User;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserLoginResDto {
    String num;
    String name;
    String email;
    String department;
    String contact;
    String profileUrl;
    Role role;
    String jwt;

    public static UserLoginResDto from(User user, String jwt){
        return UserLoginResDto.builder()
                .num(user.getNum())
                .name(user.getName())
                .email(user.getEmail())
                .department(user.getDepartment())
                .contact(user.getContact())
                .profileUrl(user.getProfileUrl())
                .role(user.getRole())
                .jwt(jwt)
                .build();
    }
}
