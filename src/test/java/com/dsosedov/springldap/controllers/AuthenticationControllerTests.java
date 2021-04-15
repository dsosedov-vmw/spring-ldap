package com.dsosedov.springldap.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {
        "spring.ldap.embedded.port=8391",
        "ldap.url=ldap://localhost:8391/dc=dsosedov,dc=com"
})
@AutoConfigureMockMvc
public class AuthenticationControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void authenticateFails() throws Exception {
        mockMvc.perform(
                post("/api/v1/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"foo\",\"password\":\"bar\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void authenticatePassesAsJohn() throws Exception {
        mockMvc.perform(
                post("/api/v1/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"johnd\",\"password\":\"johnd1\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void authenticatePassesAsJane() throws Exception {
        mockMvc.perform(
                post("/api/v1/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"janed\",\"password\":\"janed1\"}"))
                .andExpect(status().isOk());
    }

}
