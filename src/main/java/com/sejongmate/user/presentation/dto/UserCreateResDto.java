package com.sejongmate.user.presentation.dto;

import com.sejongmate.user.domain.Role;
import com.sejongmate.user.domain.User;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserCreateResDto {
    String num;
    String name;
    String email;
    String department;
    String contact;
    Role role;

    public static UserCreateResDto from(User user){
        return UserCreateResDto.builder()
                .num(user.getNum())
                .name(user.getName())
                .email(user.getEmail())
                .department(user.getDepartment())
                .contact(user.getContact())
                .role(user.getRole())
                .build();
    }
}
