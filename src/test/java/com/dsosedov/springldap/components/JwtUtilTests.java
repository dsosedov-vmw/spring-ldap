package com.dsosedov.springldap.components;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.MalformedJwtException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class JwtUtilTests {

    @Test
    public void decodeShouldFail() {
        assertThrows(MalformedJwtException.class,
                () -> JwtUtil.decode("badjwt"));
    }

    @Test
    public void encodeAndDecodeShouldSucceed() {
        String jwt = JwtUtil.encode("ben");
        Claims claims = JwtUtil.decode(jwt);

        assertDoesNotThrow(() -> UUID.fromString(claims.getId()));
        assertEquals("ben", claims.getSubject());
    }

}
