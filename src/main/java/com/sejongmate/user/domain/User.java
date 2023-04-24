package com.sejongmate.user.domain;

import com.sejongmate.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;
    private String email;
    private String profileUrl;
    private String department;
    private String contact;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;


    @Builder
    public User(String password, String name, String email, String profileUrl, String department, String contact, Role role, LocalDateTime createdDate){
        this.password = password;
        this.name = name;
        this.email = email;
        this.profileUrl = profileUrl;
        this.department = department;
        this.contact = contact;
        this.role = role;
        this.createdDate = createdDate;
    }

    public String getRoleKey(){
        return this.role.getKey();
    }

    //==비지니스 로직==//
    public User updateProfile(String name, String profileUrl){
        this.name = name;
        this.profileUrl = profileUrl;
        return this;
    }

}
