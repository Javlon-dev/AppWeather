package com.company.util;

import com.company.dto.JwtDTO;
import com.company.exception.AppBadRequestException;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Optional;

@Slf4j
public class JwtUtil {

    private final static String secretKey = "anonymous";

    private static final long forProfile = 60;
    private static final long forEmail = 3;

    public static String encodeProfile(String email) {
        return encode(email, null, forProfile);
    }

    public static String encodeEmail(String email, String profileId) {
        return encode(email, profileId, forEmail);
    }

    private static String encode(String email, String profileId, long minute) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setSubject(email);
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.signWith(SignatureAlgorithm.HS256, secretKey);

        if (minute == forProfile) {
            jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + (forProfile * 60 * 1000 * 24 * 7))); // for a week
        } else if (minute == forEmail) {
            jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + (forEmail * 60 * 1000))); // for 3 minutes
        }

        if (Optional.ofNullable(profileId).isPresent()) {
            jwtBuilder.claim("profileId", profileId);
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
            String profileId = String.valueOf(claims.get("profileId"));

            if (Optional.ofNullable(profileId).isPresent()) {
                return new JwtDTO(email, profileId);
            } else {
                return new JwtDTO(email);
            }

        } catch (JwtException e) {
            log.warn("Jwt Invalid jwt={}", jwt);
            throw new AppBadRequestException("JWT invalid!");
        }
    }

}
