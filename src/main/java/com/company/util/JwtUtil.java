package com.company.util;

import com.company.dto.JwtDTO;
import com.company.exception.AppBadRequestException;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class JwtUtil {

    private final static String secretKey = "anonymous";

    public static String encodeProfile(String email) {
        return encode(email, 60);
    }

    public static String encodeEmail(String email) {
        return encode(email, 5);
    }

    private static String encode(String email, long minute) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setSubject(email);
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.signWith(SignatureAlgorithm.HS256, secretKey);

        if (minute == 60) {
            jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + (minute * 60 * 1000 * 24 * 7))); // for a week
        } else if (minute == 5) {
            jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + (minute * 60 * 1000))); // for 5 minutes
        }

        jwtBuilder.setIssuer("Weather Production");

        return jwtBuilder.compact();
    }

    public static JwtDTO decode(String jwt) {
        try {
            JwtParser jwtParser = Jwts.parser();

            jwtParser.setSigningKey(secretKey);
            Jws jws = jwtParser.parseClaimsJws(jwt);

            Claims claims = (Claims) jws.getBody();

            String email = claims.getSubject();

            return new JwtDTO(email);

        } catch (JwtException e) {
            throw new AppBadRequestException("JWT invalid!");
        }
    }

}
