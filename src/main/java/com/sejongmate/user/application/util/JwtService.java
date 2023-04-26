package com.sejongmate.user.application.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@RequiredArgsConstructor
@Service
public class JwtService {
    @Value("${spring.jwt.secret}")
    private String secretKey;

    private Long tokenValidSecond = 1000L * 60 * 60 * 24 * 60;

    public String createJwtToken(String num) {
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam("type", "jwt")
                .claim("jwt", num)
                .setExpiration(new Date(now.getTime() + tokenValidSecond))
                .signWith(SignatureAlgorithm.HS256, secretKey )
                .compact();
    }
}
