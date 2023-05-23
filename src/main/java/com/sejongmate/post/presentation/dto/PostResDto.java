package com.sejongmate.post.presentation.dto;

import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostResDto {
    PostInfoDto postInfoDto;
    List<CommentInfoDto> commentInfoDtos;
    Boolean isScraped;
}
