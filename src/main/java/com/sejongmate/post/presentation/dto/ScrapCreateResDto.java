package com.sejongmate.post.presentation.dto;

import com.sejongmate.post.domain.Post;
import com.sejongmate.post.domain.PostScrap;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ScrapCreateResDto {
    Long id;

    public static ScrapCreateResDto from(PostScrap postScrap){
        return ScrapCreateResDto.builder()
                .id(postScrap.getId())
                .build();
    }
}
