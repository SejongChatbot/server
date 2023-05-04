package com.sejongmate.authentication.application;

import com.sejongmate.authentication.domain.JwtTokenProvider;
import com.sejongmate.authentication.presentation.dto.TokenDto;
import com.sejongmate.common.BaseException;
import com.sejongmate.common.BaseResponse;
import com.sejongmate.common.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import static com.sejongmate.common.BaseResponseStatus.INVALID_JWT;
import static com.sejongmate.common.BaseResponseStatus.NOT_EXIST_REFRESH_JWT;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    private final RedisTemplate<String, String> redisTemplate;

    public TokenDto reissueToken(String refreshToken) throws BaseException {
        // Refresh Token 검증
        jwtTokenProvider.validateToken(refreshToken);

        // Access Token 에서 User num을 가져옴
        Authentication authentication = jwtTokenProvider.getAuthentication(refreshToken);

        // Redis에서 저장된 Refresh Token 값을 가져옴
        String redisRefreshToken = redisTemplate.opsForValue().get(authentication.getName());
        if(!redisRefreshToken.equals(refreshToken)) {
            throw new BaseException(NOT_EXIST_REFRESH_JWT);
        }
        // 토큰 재발행
        TokenDto tokenDto = new TokenDto(
                jwtTokenProvider.createAccessToken(authentication),
                jwtTokenProvider.createRefreshToken(authentication)
        );

        return tokenDto;
    }
}
