package com.dsosedov.springldap.components;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuditorAwareImplTests {

    private AuditorAwareImpl auditorAware;

    @BeforeEach
    void setUp() {
        this.auditorAware = new AuditorAwareImpl();
    }

    @Test
    void getCurrentAuditorShouldReturnUsername() {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                "foo", null, Collections.emptyList()
        );
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        assertEquals("foo", auditorAware.getCurrentAuditor().get());
    }

}
