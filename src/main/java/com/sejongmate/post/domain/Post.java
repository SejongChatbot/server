package com.sejongmate.post.domain;

import com.sejongmate.common.domain.BaseTimeEntity;
import com.sejongmate.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @Enumerated(EnumType.STRING)
    private Category category;
    @Enumerated(EnumType.STRING)
    private MeetingType type;
    private String title;
    private String content;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private Integer num;

    @Builder
    public Post(User user, Category category, MeetingType type, String title, String content, LocalDateTime startAt, LocalDateTime endAt, Integer num) {
        this.user = user;
        this.category = category;
        this.type = type;
        this.title = title;
        this.content = content;
        this.startAt = startAt;
        this.endAt = endAt;
        this.num = num;
    }
}
