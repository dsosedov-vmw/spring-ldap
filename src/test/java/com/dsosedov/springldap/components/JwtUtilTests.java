package com.dsosedov.springldap.components;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.MalformedJwtException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JwtUtilTests {

    @Test
    public void decodeShouldFail() {
        assertThrows(MalformedJwtException.class,
                () -> JwtUtil.decode("badjwt"));
    }

    @Test
    public void decodeShouldSucceed() {
        Claims claims = JwtUtil.decode("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjMiLCJpYXQiOjE2MTg1MDk0NzUsInN1YiI6ImJlbiIsImlzcyI6InNwcmluZy1sZGFwIiwiZXhwIjoxNjE4NTEwMjc1fQ.b3lsJkS_Zm424YjsMN2XqeMyCho3bcCqdNFQtayD-yQ");

        assertEquals("123", claims.getId());
        assertEquals("spring-ldap", claims.getIssuer());
        assertEquals("ben", claims.getSubject());
    }

    @Test
    public void encodeAndDecodeShouldSucceed() {
        String jwtId = "123";
        String jwtIssuer = "spring-ldap";
        String jwtSubject = "ben";
        Integer jwtTimeToLive = 800000;

        String jwt = JwtUtil.encode(
                jwtId, // claim = jti
                jwtIssuer, // claim = iss
                jwtSubject, // claim = sub
                jwtTimeToLive // used to calculate expiration (claim = exp)
        );

        assertTrue(jwt.startsWith("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjMiLCJpYXQiOjE2MTg1MDk0NzUsInN1YiI6ImJlbiIsImlzcyI6InNwcmluZy1sZGFwIiwiZXhwIjoxNjE4NTEwMjc1fQ."));
    }

}
